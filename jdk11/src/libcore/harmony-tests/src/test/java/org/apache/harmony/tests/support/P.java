/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.harmony.tests.support;

import java.util.ResourceBundle;

public class P {
    private Class c;

    public void setClazz(Class c) {
        this.c = c;
    }

    public String findProp(String key) {
        return findProp(this.c, key);
    }

    private String findProp(Class cls, String key) {
        String ret = null;
        try {
            ResourceBundle b = ResourceBundle.getBundle(cls.getName());
            ret = (String) b.getObject(key);
        } catch (Exception e) {
        }
        if (ret == null && !cls.equals(Object.class) && !cls.isPrimitive()) {
            ret = findProp(cls.getSuperclass(), key);
        }
        return ret;
    }
}
