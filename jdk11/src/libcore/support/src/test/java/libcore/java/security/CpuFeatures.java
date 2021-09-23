/*
 * Copyright 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package libcore.java.security;

import android.system.Os;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.VMRuntime;

public class CpuFeatures {
    /** Machine architecture, determined from the "machine" value returned by uname() */
    private enum Arch {
        // 64bit ARM can return armv8 or aarch64.
        // 32bit ARM should return armv7 or armv7a
        ARM("^aarch.*|^arm.*"),
        // 64bit Android and Linux generally return x86_64.
        // 32bit Android and Linux generally return i686
        // Other host architectures can potentially return x86 or i386.
        X86("^x86.*|i386|i686");

        private final String machineRegex;

        Arch(String machineRegex) {
            this.machineRegex = machineRegex;
        }

        /**
         * Returns the architecture of this machine by matching against output from uname()
         * against the regex for each known family.
         */
        public static Arch currentArch() {
            String machine = Os.uname().machine;
            for (Arch type : values()) {
                if (machine.matches(type.machineRegex)) {
                    return type;
                }
            }
            throw new IllegalStateException("Unknown machine value: " + machine);
        }
    }

    private enum InstructionSet {
        ARM_32(Arch.ARM, "arm"),
        ARM_64(Arch.ARM, "arm64"),
        X86_32(Arch.X86, "x86"),
        X86_64(Arch.X86, "x86_64");

        private final Arch arch;
        private final String name;

        InstructionSet(Arch arch, String name) {
            this.arch = arch;
            this.name = name;
        }

        public Arch architecture() {
            return arch;
        }

        /**
         * Returns the current InstructionSet set by matching against the name fields above.
         */
        public static InstructionSet currentInstructionSet() {
            // Always returns one of the values from VMRuntime.ABI_TO_INSTRUCTION_SET_MAP.
            String instructionSet = VMRuntime.getCurrentInstructionSet();
            for (InstructionSet set : values()) {
                if (instructionSet.equals(set.name)) {
                    return set;
                }
            }
            throw new IllegalStateException("Unknown instruction set: " + instructionSet);
        }
    }

    private CpuFeatures() {
    }

    /**
     * Returns true if this device has hardware AES support as determined by BoringSSL.
     */
    public static boolean isAesHardwareAccelerated() {
        try {
            Class<?> nativeCrypto = Class.forName("com.android.org.conscrypt.NativeCrypto");
            Method EVP_has_aes_hardware = nativeCrypto.getDeclaredMethod("EVP_has_aes_hardware");
            EVP_has_aes_hardware.setAccessible(true);
            return ((Integer) EVP_has_aes_hardware.invoke(null)) == 1;
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException
                | IllegalAccessException | IllegalArgumentException ignored) {
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
        }

        return false;
    }

    /**
     * Returns true if this device should have hardware AES support based on CPU information.
     *
     * A return value of false means that acceleration isn't expected, but it may still be available
     * e.g. via bridging to a native library in an emulated environment.
     */
    public static boolean isKnownToSupportHardwareAes() {
        Arch architecture = Arch.currentArch();
        InstructionSet instructionSet = InstructionSet.currentInstructionSet();

        if (!instructionSet.architecture().equals(architecture)) {
            // Different architectures imply an emulated environment, so unable to determine if
            // hardware acceleration is expected.  Assume not.
            return false;
        }

        if (architecture.equals(Arch.ARM)) {
            // All ARM CPUs (32 and 64 bit) with the "aes" feature should have hardware AES.
            return cpuFieldContainsAes("Features");
        } else if (instructionSet.equals(InstructionSet.X86_64)) {
            // x86 CPUs with the "aes" flag and running in 64bit mode should have hardware AES.
            // Hardware AES is not *expected* in 32bit mode, but may be available.
            return cpuFieldContainsAes("flags");
        }
        return false;
    }


    /**
     * Returns true if any line in the output from /proc/cpuinfo matches the provided
     * field name and contains the word "aes" in its list of values.
     *
     * Example line from /proc/cpuinfo: Features	: fp asimd evtstrm aes pmull sha1 sha2 crc32
     */
    private static boolean cpuFieldContainsAes(String fieldName) {
        try (BufferedReader br = new BufferedReader(new FileReader("/proc/cpuinfo"))) {
            String regex = "^" + fieldName + "\\s*:.*\\baes\\b.*";
            String line;
            while ((line = br.readLine()) != null) {
                if (line.matches(regex)) {
                    return true;
                }
            }
        } catch (IOException ignored) {
        }
        return false;
    }
}
