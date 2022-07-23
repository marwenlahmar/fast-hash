package com.mnlr;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FastHash {

  private final int maxChunkSize;
  private final MessageDigest messageDigest;

  private FastHash(String algorithm, int maxChunkSize) {
    this.maxChunkSize = maxChunkSize;
    try {
      messageDigest = MessageDigest.getInstance(algorithm);
    } catch (NoSuchAlgorithmException e) {
      throw new FastHashException("Class init failure", e);
    }
  }

  /**
   * Build a new instance of FastHash with provided algorithm and chunk size
   *
   * @param algorithm the name of the algorithm requested. See the MessageDigest section in the <a
   *     href="{@docRoot}/../specs/security/standard-names.html#messagedigest-algorithms">Java
   *     Security Standard Algorithm Names Specification</a> for information about standard
   *     algorithm names.
   * @param chunkSize the maximum chunk of data to read from the beginning and end of the {@link
   *     SeekableDataStream}
   * @return new instance of {@link FastHash}
   */
  public static FastHash getInstance(String algorithm, int chunkSize) {
    return new FastHash(algorithm, chunkSize);
  }

  /**
   * Generate a hash value of the provided {@link SeekableDataStream} based on the algorithm and
   * chunk size
   *
   * @param dataStream must implement the {@link SeekableDataStream}
   * @return digest in hex format
   */
  public String digest(SeekableDataStream dataStream) {

    try (dataStream) {

      int chunkSize = (int) Math.min(dataStream.length(), maxChunkSize);
      if (chunkSize < 1) {
        throw new FastHashException("Data size size must be > 0 ");
      }

      // read the first chunk
      dataStream.seek(0);
      byte[] firstChunk = new byte[chunkSize];
      dataStream.readFully(firstChunk);

      // seek to the second chunk and read it
      long seekTo = dataStream.length() - chunkSize;
      dataStream.seek(seekTo);
      byte[] secondChunk = new byte[chunkSize];
      dataStream.readFully(secondChunk);
      StringBuilder sb = new StringBuilder();

      // We need to synchronize to make the call is thread safe
      synchronized (this) {
        // update and digest the first chunk followed by the data length followed by the second
        // chunk
        messageDigest.update(firstChunk);
        messageDigest.update(String.valueOf(dataStream.length()).getBytes());
        messageDigest.update(secondChunk);
        for (byte aByte : messageDigest.digest()) {
          sb.append(String.format("%02X", aByte));
        }

        // reset messageDigest for next digest
        messageDigest.reset();
      }
      return sb.toString();
    } catch (IOException e) {
      throw new FastHashException("Failed to calculate hash", e);
    }
  }
}
