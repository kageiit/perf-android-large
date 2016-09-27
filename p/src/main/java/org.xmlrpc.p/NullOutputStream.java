package org.xmlrpc.p;

import java.io.IOException;
import java.io.OutputStream;

/**Writes to nowhere*/
final class NullOutputStream extends OutputStream {
  @Override
  public void write(int b) throws IOException {
  }
}