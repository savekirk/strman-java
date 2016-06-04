@file:JvmMultifileClass
@file:JvmName("StrmanKt")


package strmankt

import java.util.*


/**
 * Appends Strings to value

 * @param value   initial String
 * *
 * @param appends an array of strings to append
 * *
 * @return full String
 */
fun append(value: String, vararg appends: String): String {
    if (appends.isEmpty()) {
        return value
    }

    val toJoin = appends.reduce { s1, s2 -> s1 + s2 }

    return value + toJoin
}

/**
 * Returns an array with strings between start and end.

 * @param value input
 * *
 * @param start start
 * *
 * @param end   end
 * *
 * @return Array containing different parts between start and end.
 */

fun between(value: String, start: String, end: String): Array<String> {
    val result = value.split(start, end).filterIndexed { i, s -> i % 2 == 1 }.toTypedArray()
    return  if(result.isEmpty()) arrayOf(value) else result
}

/**
 * Returns a String array consisting of the characters in the String.

 * @param value input
 * *
 * @return character array
 */
fun chars(value: String) = value.split("").filter { it.isNotEmpty() }.toTypedArray()

/**
 * Replace consecutive whitespace characters with a single space.

 * @param value input String
 * *
 * @return collapsed String
 */
fun collapseWhitespace(value: String) = value.trim().replace("\\s\\s+".toRegex(), " ")


/**
 * Verifies that the needle is contained in the value.

 * @param value         to search
 * *
 * @param needle        to find
 * *
 * @param caseSensitive true or false
 * *
 * @return true if found else false.
 */
@JvmOverloads
fun contains(value: String, needle: String, caseSensitive: Boolean = false): Boolean {
    return  if (caseSensitive)
        value.contains(needle)
    else
        value.toLowerCase().contains(needle.toLowerCase())
}

/**
 * Verifies that all needles are contained in value

 * @param value         input String to search
 * *
 * @param needles       needles to find
 * *
 * @param caseSensitive true or false
 * *
 * @return true if all needles are found else false.
 */
@JvmOverloads
fun containsAll(value: String, needles: Array<String>, caseSensitive: Boolean = false): Boolean {
    return needles.all { contains(value, it, caseSensitive) }
}


/**
 * Verifies that one or more of needles are contained in value.

 * @param value         input
 * *
 * @param needles       needles to search
 * *
 * @param caseSensitive true or false
 * *
 * @return boolean true if any needle is found else false
 */
@JvmOverloads
fun containsAny(value: String, needles: Array<String>, caseSensitive: Boolean = false): Boolean {
    return  needles.any { contains(value, it, caseSensitive) }
}

/**
 * Count the number of times substr appears in value

 * @param value            input
 * *
 * @param subStr           search string
 * *
 * @param caseSensitive    whether search should be case sensitive
 * *
 * @param allowOverlapping boolean to take into account overlapping
 * *
 * @return count of times substring exists
 */
@JvmOverloads
fun countSubstr(value: String, subStr: String, caseSensitive: Boolean = true, allowOverlapping: Boolean = false): Long {
    return countSubstr(if (caseSensitive) value else value.toLowerCase(), if (caseSensitive) subStr else subStr.toLowerCase(), allowOverlapping, 0L)
}

tailrec private fun countSubstr(value: String, subStr: String, allowOverlapping: Boolean, count: Long): Long {
    var count = count
    val position = value.indexOf(subStr)
    if (position == -1) {
        return count
    }

    val offset =  position + if (!allowOverlapping) subStr.length else 1

    return countSubstr(value.substring(offset), subStr, allowOverlapping, ++count)
}

/**
 * Test if value ends with search.

 * @param value         input string
 * *
 * @param search        string to search
 * *
 * @param position      position till which you want to search.
 * *
 * @param caseSensitive true or false
 * *
 * @return true or false
 */
@JvmOverloads
fun endsWith(value: String, search: String, position: Int = value.length, caseSensitive: Boolean = true): Boolean {
    val remainingLength = position - search.length
    return if (caseSensitive)
        value.indexOf(search, remainingLength) > -1
    else
        value.toLowerCase().indexOf(search.toLowerCase(), remainingLength) > -1
}

fun endsWith(value: String, search: String, caseSensitive: Boolean = true): Boolean {
    return value.endsWith(search, !caseSensitive)
}

/**
 * Ensures that the value begins with prefix. If it doesn't exist, it's prepended.

 * @param value         input
 * *
 * @param prefix        prefix
 * *
 * @param caseSensitive true or false
 * *
 * @return string with prefix if it was not present.
 */
@JvmOverloads
fun ensureLeft(value: String, prefix: String, caseSensitive: Boolean = true): String {
    return if (value.startsWith(prefix, ignoreCase = !caseSensitive)) value else prefix + value
}

/**
 * Decodes data encoded with MIME base64

 * @param value The data to decode
 * *
 * @return decoded data
 */
fun base64Decode(value: String): String {
    return String(Base64.getDecoder().decode(value))
}

/**
 * Encodes data with MIME base64.

 * @param value The data to encode
 * *
 * @return The encoded String
 */
fun base64Encode(value: String): String {
    return Base64.getEncoder().encodeToString(value.toByteArray())
}

/**
 * Convert binary unicode (16 digits) string to string chars

 * @param value The value to decode
 * *
 * @return The decoded String
 */
fun binDecode(value: String): String {
    value
    return decode(value, 16, 2)
}

/**
 * Convert string chars to binary unicode (16 digits)

 * @param value The value to encode
 * *
 * @return String in binary format
 */
fun binEncode(value: String): String {
    return encode(value, 16, 2)
}

fun decode(value: String, digits: Int, radix: Int): String {
    return value.split("(?<=\\G.{$digits})".toRegex()).map{ String(Character.toChars(Integer.parseInt(it, radix))) }.toString()
}

fun encode(value: String, digits: Int, radix: Int): String {
    return value.toCharArray().map { Integer.toString(it.toInt(), radix).padStart(digits,'0') }.toString()
}

/**
 * Returns a new string of a given length such that the beginning of the string is padded.

 * @param value  The input String
 * *
 * @param pad    The pad
 * *
 * @param length Length of the String we want
 * *
 * @return Padded String
 */
fun leftPad(value: String, pad: String, length: Int): String {
    if (value.length > length) {
        return value
    }
    val sb = StringBuilder(length)
    for (i in 1..(length - value.length))
        sb.append(pad)
    sb.append(value)

    return sb.toString()
}



