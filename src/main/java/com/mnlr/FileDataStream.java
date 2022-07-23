package com.mnlr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Objects;

public class FileDataStream implements SeekableDataStream {

  private final RandomAccessFile randomAccessFile;

  private final long length;

  public FileDataStream(File file) throws FileNotFoundException {
    Objects.requireNonNull(file);
    this.length = file.length();
    this.randomAccessFile = new RandomAccessFile(file, "r");
  }

  @Override
  public void seek(long position) throws IOException {
    if (position > randomAccessFile.length()) {
      throw new IOException("Seek position " + position + " is out of bound");
    }
    randomAccessFile.seek(position);
  }

  @Override
  public void readFully(byte[] buffer) throws IOException {
    randomAccessFile.readFully(buffer);
  }

  @Override
  public long length() {
    return length;
  }

  @Override
  public void close() throws IOException {
    randomAccessFile.close();
  }
}
