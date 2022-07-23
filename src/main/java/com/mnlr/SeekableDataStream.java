package com.mnlr;

import java.io.Closeable;
import java.io.IOException;

public interface SeekableDataStream extends Closeable {

  void seek(long position) throws IOException;

  void readFully(byte[] buffer) throws IOException;

  long length();
}
