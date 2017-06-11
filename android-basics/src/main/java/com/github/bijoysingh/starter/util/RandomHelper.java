package com.github.bijoysingh.starter.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Some common function used often and are difficult to classify
 * Created by bijoy on 5/12/17.
 */

public class RandomHelper {

  /**
   * Get a random String
   *
   * @return the random string.
   */
  public static String getRandom() {
    return getRandomString(16);
  }

  /**
   * Get a random string with a fixed length
   *
   * @param length the length of the string
   * @return the String
   */
  public static String getRandomString(int length) {
    return new BigInteger(length * 5, getSecureRandom()).toString(32);
  }

  /**
   * Get a random Big Integer with a approx fixed length
   *
   * @param length the approx length of the integer
   * @return the random int
   */
  public static BigInteger getRandomInteger(int length) {
    int size = (int) Math.ceil(Math.log(Math.pow(10.0, length)) / Math.log(2));
    return new BigInteger(size, RandomHelper.getSecureRandom());
  }


  /**
   * Get a random Big Integer with a fixed number of bits
   *
   * @param bits the number of bits
   * @return the random int
   */
  public static BigInteger getRandomIntegerBits(int bits) {
    return new BigInteger(bits, RandomHelper.getSecureRandom());
  }

  /**
   * Get the secure random object
   *
   * @return the object
   */
  private static SecureRandom getSecureRandom() {
    return new SecureRandom();
  }
}
