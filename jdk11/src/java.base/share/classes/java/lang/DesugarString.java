/*
 * Copyright (c) 1994, 2018, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Arrays;
import java.util.Objects;
import java.util.Spliterator;
import java.util.StringJoiner;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The {@code String} class represents character strings. All
 * string literals in Java programs, such as {@code "abc"}, are
 * implemented as instances of this class.
 * <p>
 * Strings are constant; their values cannot be changed after they
 * are created. String buffers support mutable strings.
 * Because String objects are immutable they can be shared. For example:
 * <blockquote><pre>
 *     String str = "abc";
 * </pre></blockquote><p>
 * is equivalent to:
 * <blockquote><pre>
 *     char data[] = {'a', 'b', 'c'};
 *     String str = new String(data);
 * </pre></blockquote><p>
 * Here are some more examples of how strings can be used:
 * <blockquote><pre>
 *     System.out.println("abc");
 *     String cde = "cde";
 *     System.out.println("abc" + cde);
 *     String c = "abc".substring(2,3);
 *     String d = cde.substring(1, 2);
 * </pre></blockquote>
 * <p>
 * The class {@code String} includes methods for examining
 * individual characters of the sequence, for comparing strings, for
 * searching strings, for extracting substrings, and for creating a
 * copy of a string with all characters translated to uppercase or to
 * lowercase. Case mapping is based on the Unicode Standard version
 * specified by the {@link java.lang.Character Character} class.
 * <p>
 * The Java language provides special support for the string
 * concatenation operator (&nbsp;+&nbsp;), and for conversion of
 * other objects to strings. For additional information on string
 * concatenation and conversion, see <i>The Java&trade; Language Specification</i>.
 *
 * <p> Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 *
 * <p>A {@code String} represents a string in the UTF-16 format
 * in which <em>supplementary characters</em> are represented by <em>surrogate
 * pairs</em> (see the section <a href="Character.html#unicode">Unicode
 * Character Representations</a> in the {@code Character} class for
 * more information).
 * Index values refer to {@code char} code units, so a supplementary
 * character uses two positions in a {@code String}.
 * <p>The {@code String} class provides methods for dealing with
 * Unicode code points (i.e., characters), in addition to those for
 * dealing with Unicode code units (i.e., {@code char} values).
 *
 * <p>Unless otherwise noted, methods for comparing Strings do not take locale
 * into account.  The {@link java.text.Collator} class provides methods for
 * finer-grain, locale-sensitive String comparison.
 *
 * @implNote The implementation of the string concatenation operator is left to
 * the discretion of a Java compiler, as long as the compiler ultimately conforms
 * to <i>The Java&trade; Language Specification</i>. For example, the {@code javac} compiler
 * may implement the operator with {@code StringBuffer}, {@code StringBuilder},
 * or {@code java.lang.invoke.StringConcatFactory} depending on the JDK version. The
 * implementation of string conversion is typically through the method {@code toString},
 * defined by {@code Object} and inherited by all classes in Java.
 *
 * @author  Lee Boynton
 * @author  Arthur van Hoff
 * @author  Martin Buchholz
 * @author  Ulf Zibis
 * @see     java.lang.Object#toString()
 * @see     java.lang.StringBuffer
 * @see     java.lang.StringBuilder
 * @see     java.nio.charset.Charset
 * @since   1.0
 * @jls     15.18.1 String Concatenation Operator +
 */

public final class DesugarString {

  /**
   * Returns a new String composed of copies of the
   * {@code CharSequence elements} joined together with a copy of
   * the specified {@code delimiter}.
   *
   * <blockquote>For example,
   * <pre>{@code
   *     String message = String.join("-", "Java", "is", "cool");
   *     // message returned is: "Java-is-cool"
   * }</pre></blockquote>
   *
   * Note that if an element is null, then {@code "null"} is added.
   *
   * @param  delimiter the delimiter that separates each element
   * @param  elements the elements to join together.
   *
   * @return a new {@code String} that is composed of the {@code elements}
   *         separated by the {@code delimiter}
   *
   * @throws NullPointerException If {@code delimiter} or {@code elements}
   *         is {@code null}
   *
   * @see java.util.StringJoiner
   * @since 1.8
   */
  public static String join(CharSequence delimiter, CharSequence... elements) {
    Objects.requireNonNull(delimiter);
    Objects.requireNonNull(elements);
    // Number of elements not likely worth Arrays.stream overhead.
    StringJoiner joiner = new StringJoiner(delimiter);
    for (CharSequence cs: elements) {
      joiner.add(cs);
    }
    return joiner.toString();
  }

  /**
   * Returns a new {@code String} composed of copies of the
   * {@code CharSequence elements} joined together with a copy of the
   * specified {@code delimiter}.
   *
   * <blockquote>For example,
   * <pre>{@code
   *     List<String> strings = List.of("Java", "is", "cool");
   *     String message = String.join(" ", strings);
   *     //message returned is: "Java is cool"
   *
   *     Set<String> strings =
   *         new LinkedHashSet<>(List.of("Java", "is", "very", "cool"));
   *     String message = String.join("-", strings);
   *     //message returned is: "Java-is-very-cool"
   * }</pre></blockquote>
   *
   * Note that if an individual element is {@code null}, then {@code "null"} is added.
   *
   * @param  delimiter a sequence of characters that is used to separate each
   *         of the {@code elements} in the resulting {@code String}
   * @param  elements an {@code Iterable} that will have its {@code elements}
   *         joined together.
   *
   * @return a new {@code String} that is composed from the {@code elements}
   *         argument
   *
   * @throws NullPointerException If {@code delimiter} or {@code elements}
   *         is {@code null}
   *
   * @see    #join(CharSequence,CharSequence...)
   * @see    java.util.StringJoiner
   * @since 1.8
   */
  public static String join(CharSequence delimiter,
      Iterable<? extends CharSequence> elements) {
    Objects.requireNonNull(delimiter);
    Objects.requireNonNull(elements);
    StringJoiner joiner = new StringJoiner(delimiter);
    for (CharSequence cs: elements) {
      joiner.add(cs);
    }
    return joiner.toString();
  }

  /**
   * Returns a string whose value is this string, with all leading
   * and trailing {@link Character#isWhitespace(int) white space}
   * removed.
   * <p>
   * If this {@code String} object represents an empty string,
   * or if all code points in this string are
   * {@link Character#isWhitespace(int) white space}, then an empty string
   * is returned.
   * <p>
   * Otherwise, returns a substring of this string beginning with the first
   * code point that is not a {@link Character#isWhitespace(int) white space}
   * up to and including the last code point that is not a
   * {@link Character#isWhitespace(int) white space}.
   * <p>
   * This method may be used to strip
   * {@link Character#isWhitespace(int) white space} from
   * the beginning and end of a string.
   *
   * @return  a string whose value is this string, with all leading
   *          and trailing white space removed
   *
   * @see Character#isWhitespace(int)
   *
   * @since 11
   */
  public static String strip(String text) {
    String ret = isLatin1(text) ? StringLatin1.strip(text.value())
        : StringUTF16.strip(text.value());
    return ret == null ? text : ret;
  }

  /**
   * Returns a string whose value is this string, with all leading
   * {@link Character#isWhitespace(int) white space} removed.
   * <p>
   * If this {@code String} object represents an empty string,
   * or if all code points in this string are
   * {@link Character#isWhitespace(int) white space}, then an empty string
   * is returned.
   * <p>
   * Otherwise, returns a substring of this string beginning with the first
   * code point that is not a {@link Character#isWhitespace(int) white space}
   * up to to and including the last code point of this string.
   * <p>
   * This method may be used to trim
   * {@link Character#isWhitespace(int) white space} from
   * the beginning of a string.
   *
   * @return  a string whose value is this string, with all leading white
   *          space removed
   *
   * @see Character#isWhitespace(int)
   *
   * @since 11
   */
  public static String stripLeading(String text) {
    String ret = isLatin1(text) ? StringLatin1.stripLeading(text.value())
        : StringUTF16.stripLeading(text.value());
    return ret == null ? text : ret;
  }

  /**
   * Returns a string whose value is this string, with all trailing
   * {@link Character#isWhitespace(int) white space} removed.
   * <p>
   * If this {@code String} object represents an empty string,
   * or if all characters in this string are
   * {@link Character#isWhitespace(int) white space}, then an empty string
   * is returned.
   * <p>
   * Otherwise, returns a substring of this string beginning with the first
   * code point of this string up to and including the last code point
   * that is not a {@link Character#isWhitespace(int) white space}.
   * <p>
   * This method may be used to trim
   * {@link Character#isWhitespace(int) white space} from
   * the end of a string.
   *
   * @return  a string whose value is this string, with all trailing white
   *          space removed
   *
   * @see Character#isWhitespace(int)
   *
   * @since 11
   */
  public static String stripTrailing(String text) {
    String ret = isLatin1(text) ? StringLatin1.stripTrailing(text.value())
        : StringUTF16.stripTrailing(text.value());
    return ret == null ? text : ret;
  }

  /**
   * Returns {@code true} if the string is empty or contains only
   * {@link Character#isWhitespace(int) white space} codepoints,
   * otherwise {@code false}.
   *
   * @return {@code true} if the string is empty or contains only
   *         {@link Character#isWhitespace(int) white space} codepoints,
   *         otherwise {@code false}
   *
   * @see Character#isWhitespace(int)
   *
   * @since 11
   */
  public static boolean isBlank(String text) {
    return indexOfNonWhitespace(text) == text.length();
  }

  private static int indexOfNonWhitespace(String text) {
    if (isLatin1(text)) {
      return StringLatin1.indexOfNonWhitespace(text.value());
    } else {
      return StringUTF16.indexOfNonWhitespace(text.value());
    }
  }

  /**
   * Returns a stream of lines extracted from this string,
   * separated by line terminators.
   * <p>
   * A <i>line terminator</i> is one of the following:
   * a line feed character {@code "\n"} (U+000A),
   * a carriage return character {@code "\r"} (U+000D),
   * or a carriage return followed immediately by a line feed
   * {@code "\r\n"} (U+000D U+000A).
   * <p>
   * A <i>line</i> is either a sequence of zero or more characters
   * followed by a line terminator, or it is a sequence of one or
   * more characters followed by the end of the string. A
   * line does not include the line terminator.
   * <p>
   * The stream returned by this method contains the lines from
   * this string in the order in which they occur.
   *
   * @apiNote This definition of <i>line</i> implies that an empty
   *          string has zero lines and that there is no empty line
   *          following a line terminator at the end of a string.
   *
   * @implNote This method provides better performance than
   *           split("\R") by supplying elements lazily and
   *           by faster search of new line terminators.
   *
   * @return  the stream of lines extracted from this string
   *
   * @since 11
   */
  public static Stream<String> lines(String text) {
    return isLatin1(text) ? StringLatin1.lines(text.value())
        : StringUTF16.lines(text.value());
  }

  /**
   * Returns a stream of {@code int} zero-extending the {@code char} values
   * from this sequence.  Any char which maps to a <a
   * href="{@docRoot}/java.base/java/lang/Character.html#unicode">surrogate code
   * point</a> is passed through uninterpreted.
   *
   * @return an IntStream of char values from this sequence
   * @since 9
   */
  public static IntStream chars(String text) {
    return StreamSupport.intStream(
        isLatin1(text) ? new StringLatin1.CharsSpliterator(text.value(), Spliterator.IMMUTABLE)
            : new StringUTF16.CharsSpliterator(text.value(), Spliterator.IMMUTABLE),
        false);
  }


  /**
   * Returns a stream of code point values from this sequence.  Any surrogate
   * pairs encountered in the sequence are combined as if by {@linkplain
   * Character#toCodePoint Character.toCodePoint} and the result is passed
   * to the stream. Any other code units, including ordinary BMP characters,
   * unpaired surrogates, and undefined code units, are zero-extended to
   * {@code int} values which are then passed to the stream.
   *
   * @return an IntStream of Unicode code points from this sequence
   * @since 9
   */
  public static IntStream codePoints(String text) {
    return StreamSupport.intStream(
        isLatin1(text) ? new StringLatin1.CharsSpliterator(text.value(), Spliterator.IMMUTABLE)
            : new StringUTF16.CodePointsSpliterator(text.value(), Spliterator.IMMUTABLE),
        false);
  }

  /**
   * Returns a canonical representation for the string object.
   * <p>
   * A pool of strings, initially empty, is maintained privately by the
   * class {@code String}.
   * <p>
   * When the intern method is invoked, if the pool already contains a
   * string equal to this {@code String} object as determined by
   * the {@link #equals(Object)} method, then the string from the pool is
   * returned. Otherwise, this {@code String} object is added to the
   * pool and a reference to this {@code String} object is returned.
   * <p>
   * It follows that for any two strings {@code s} and {@code t},
   * {@code s.intern() == t.intern()} is {@code true}
   * if and only if {@code s.equals(t)} is {@code true}.
   * <p>
   * All literal strings and string-valued constant expressions are
   * interned. String literals are defined in section 3.10.5 of the
   * <cite>The Java&trade; Language Specification</cite>.
   *
   * @return  a string that has the same contents as this string, but is
   *          guaranteed to be from a pool of unique strings.
   * @jls 3.10.5 String Literals
   */
  public native DesugarString intern();

  /**
   * Returns a string whose value is the concatenation of this
   * string repeated {@code count} times.
   * <p>
   * If this string is empty or count is zero then the empty
   * string is returned.
   *
   * @param   count number of times to repeat
   *
   * @return  A string composed of this string repeated
   *          {@code count} times or the empty string if this
   *          string is empty or count is zero
   *
   * @throws  IllegalArgumentException if the {@code count} is
   *          negative.
   *
   * @since 11
   */
  public static String repeat(String text, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count is negative: " + count);
        }
        if (count == 1) {
      return text;
        }
    final int len = text.value().length;
        if (len == 0 || count == 0) {
            return "";
        }
        if (len == 1) {
            final byte[] single = new byte[count];
      Arrays.fill(single, text.value()[0]);
      return new String(single, text.coder());
        }
        if (Integer.MAX_VALUE / count < len) {
            throw new OutOfMemoryError("Repeating " + len + " bytes String " + count +
                    " times will produce a String exceeding maximum size.");
        }
        final int limit = len * count;
        final byte[] multiple = new byte[limit];
    System.arraycopy(text.value(), 0, multiple, 0, len);
        int copied = len;
        for (; copied < limit - copied; copied <<= 1) {
            System.arraycopy(multiple, 0, multiple, copied, copied);
        }
        System.arraycopy(multiple, 0, multiple, copied, limit - copied);
    return new String(multiple, text.coder());
    }

  private static boolean isLatin1(String text) {
    return String.COMPACT_STRINGS && text.coder() == String.LATIN1;
    }
}
