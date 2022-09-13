/*
 * Copyright (c) 2002, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package java.lang;

/** Desugar-companion class for {@link Character}. */
public class DesugarCharacter {

    /**
     * The minimum value of a
     * <a href="http://www.unicode.org/glossary/#high_surrogate_code_unit">
     * Unicode high-surrogate code unit</a>
     * in the UTF-16 encoding, constant {@code '\u005CuD800'}.
     * A high-surrogate is also known as a <i>leading-surrogate</i>.
     *
     * @since 1.5
     */

    public static final char MIN_HIGH_SURROGATE = '\uD800';
    /**
     * The maximum value of a
     * <a href="http://www.unicode.org/glossary/#high_surrogate_code_unit">
     * Unicode high-surrogate code unit</a>
     * in the UTF-16 encoding, constant {@code '\u005CuDBFF'}.
     * A high-surrogate is also known as a <i>leading-surrogate</i>.
     *
     * @since 1.5
     */
    public static final char MAX_HIGH_SURROGATE = '\uDBFF';

    /**
     * The minimum value of a
     * <a href="http://www.unicode.org/glossary/#low_surrogate_code_unit">
     * Unicode low-surrogate code unit</a>
     * in the UTF-16 encoding, constant {@code '\u005CuDC00'}.
     * A low-surrogate is also known as a <i>trailing-surrogate</i>.
     *
     * @since 1.5
     */
    public static final char MIN_LOW_SURROGATE  = '\uDC00';

    /**
     * The maximum value of a
     * <a href="http://www.unicode.org/glossary/#low_surrogate_code_unit">
     * Unicode low-surrogate code unit</a>
     * in the UTF-16 encoding, constant {@code '\u005CuDFFF'}.
     * A low-surrogate is also known as a <i>trailing-surrogate</i>.
     *
     * @since 1.5
     */
    public static final char MAX_LOW_SURROGATE  = '\uDFFF';

    /**
     * The minimum value of a Unicode surrogate code unit in the
     * UTF-16 encoding, constant {@code '\u005CuD800'}.
     *
     * @since 1.5
     */
    public static final char MIN_SURROGATE = MIN_HIGH_SURROGATE;

    /**
     * The maximum value of a Unicode surrogate code unit in the
     * UTF-16 encoding, constant {@code '\u005CuDFFF'}.
     *
     * @since 1.5
     */
    public static final char MAX_SURROGATE = MAX_LOW_SURROGATE;

    /**
     * The minimum value of a
     * <a href="http://www.unicode.org/glossary/#supplementary_code_point">
     * Unicode supplementary code point</a>, constant {@code U+10000}.
     *
     * @since 1.5
     */
    public static final int MIN_SUPPLEMENTARY_CODE_POINT = 0x010000;


    /**
     * Determines whether the specified character (Unicode code point)
     * is in the <a href="#BMP">Basic Multilingual Plane (BMP)</a>.
     * Such code points can be represented using a single {@code char}.
     *
     * @param  codePoint the character (Unicode code point) to be tested
     * @return {@code true} if the specified code point is between
     *         {@link #MIN_VALUE} and {@link #MAX_VALUE} inclusive;
     *         {@code false} otherwise.
     * @since  1.7
     */
    public static boolean isBmpCodePoint(int codePoint) {
        return codePoint >>> 16 == 0;
        // Optimized form of:
        //     codePoint >= MIN_VALUE && codePoint <= MAX_VALUE
        // We consistently use logical shift (>>>) to facilitate
        // additional runtime optimizations.
    }

    /**
     * Determines if the given {@code char} value is a Unicode
     * <i>surrogate code unit</i>.
     *
     * <p>Such values do not represent characters by themselves,
     * but are used in the representation of
     * <a href="#supplementary">supplementary characters</a>
     * in the UTF-16 encoding.
     *
     * <p>A char value is a surrogate code unit if and only if it is either
     * a {@linkplain #isLowSurrogate(char) low-surrogate code unit} or
     * a {@linkplain #isHighSurrogate(char) high-surrogate code unit}.
     *
     * @param  ch the {@code char} value to be tested.
     * @return {@code true} if the {@code char} value is between
     *         {@link #MIN_SURROGATE} and
     *         {@link #MAX_SURROGATE} inclusive;
     *         {@code false} otherwise.
     * @since  1.7
     */

    public static boolean isSurrogate(char ch) {
        return ch >= MIN_SURROGATE && ch < (MAX_SURROGATE + 1);
    }
    /**
     * Returns the leading surrogate (a
     * <a href="http://www.unicode.org/glossary/#high_surrogate_code_unit">
     * high surrogate code unit</a>) of the
     * <a href="http://www.unicode.org/glossary/#surrogate_pair">
     * surrogate pair</a>
     * representing the specified supplementary character (Unicode
     * code point) in the UTF-16 encoding.  If the specified character
     * is not a
     * <a href="Character.html#supplementary">supplementary character</a>,
     * an unspecified {@code char} is returned.
     *
     * <p>If
     * {@link #isSupplementaryCodePoint isSupplementaryCodePoint(x)}
     * is {@code true}, then
     * {@link #isHighSurrogate isHighSurrogate}{@code (highSurrogate(x))} and
     * {@link #toCodePoint toCodePoint}{@code (highSurrogate(x), }{@link #lowSurrogate lowSurrogate}{@code (x)) == x}
     * are also always {@code true}.
     *
     * @param   codePoint a supplementary character (Unicode code point)
     * @return  the leading surrogate code unit used to represent the
     *          character in the UTF-16 encoding
     * @since   1.7
     */
    public static char highSurrogate(int codePoint) {
        return (char) ((codePoint >>> 10)
            + (MIN_HIGH_SURROGATE - (MIN_SUPPLEMENTARY_CODE_POINT >>> 10)));
    }

    /**
     * Returns the trailing surrogate (a
     * <a href="http://www.unicode.org/glossary/#low_surrogate_code_unit">
     * low surrogate code unit</a>) of the
     * <a href="http://www.unicode.org/glossary/#surrogate_pair">
     * surrogate pair</a>
     * representing the specified supplementary character (Unicode
     * code point) in the UTF-16 encoding.  If the specified character
     * is not a
     * <a href="Character.html#supplementary">supplementary character</a>,
     * an unspecified {@code char} is returned.
     *
     * <p>If
     * {@link #isSupplementaryCodePoint isSupplementaryCodePoint(x)}
     * is {@code true}, then
     * {@link #isLowSurrogate isLowSurrogate}{@code (lowSurrogate(x))} and
     * {@link #toCodePoint toCodePoint}{@code (}{@link #highSurrogate highSurrogate}{@code (x), lowSurrogate(x)) == x}
     * are also always {@code true}.
     *
     * @param   codePoint a supplementary character (Unicode code point)
     * @return  the trailing surrogate code unit used to represent the
     *          character in the UTF-16 encoding
     * @since   1.7
     */
    public static char lowSurrogate(int codePoint) {
        return (char) ((codePoint & 0x3ff) + MIN_LOW_SURROGATE);
    }

  private DesugarCharacter() {}
}
