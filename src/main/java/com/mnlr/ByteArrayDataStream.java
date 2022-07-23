package com.mnlr;

import java.io.IOException;
import java.util.Objects;

public class ByteArrayDataStream implements SeekableDataStream {

  private final byte[] bytes;
  private int position = 0;

  public ByteArrayDataStream(byte[] bytes) {
    Objects.requireNonNull(bytes);
    this.bytes = bytes;
  }

  @Override
  public void seek(long position) throws IOException {
    if (position > bytes.length) {
      throw new IOException("Seek position " + position + " is out of bound");
    }
    this.position = (int) position;
  }

  @Override
  public void readFully(byte[] buffer) {
    Objects.requireNonNull(buffer);
    System.arraycopy(bytes, position, buffer, 0, buffer.length);
  }

  @Override
  public long length() {
    return bytes.length;
  }

  @Override
  public void close() {}
}
