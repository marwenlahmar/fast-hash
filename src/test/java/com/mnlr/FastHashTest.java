package com.mnlr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.jupiter.api.Test;

public class FastHashTest {
  @Test
  public void file1_65536MaxChunk() throws Exception {

    FastHash fastHash = FastHash.getInstance("SHA1", 64 * 1024);
    String digest =
        fastHash.digest(
            new FileDataStream(
                new File(
                    Objects.requireNonNull(FastHashTest.class.getResource("/file1")).getPath())));

    assertEquals("D7991CAAB55A3D07B3ADC51284CE275CA504FB77", digest);
  }

  @Test
  public void file2_1024MaxChunk() throws Exception {

    FastHash fastHash = FastHash.getInstance("SHA1", 1024);
    String digest =
        fastHash.digest(
            new FileDataStream(
                new File(
                    Objects.requireNonNull(FastHashTest.class.getResource("/file2")).getPath())));

    assertEquals("1A077C215F12D21C6BF68F58C8A43EC4ADDC98C1", digest);
  }

  @Test
  public void bytes1_65536MaxChunk() throws Exception {
    byte[] bytes;
    try (InputStream inputStream =
        Objects.requireNonNull(FastHashTest.class.getResourceAsStream("/file1"))) {
      bytes = inputStream.readAllBytes();
    }

    FastHash fastHash = FastHash.getInstance("SHA1", 64 * 1024);
    String digest = fastHash.digest(new ByteArrayDataStream(bytes));

    assertEquals("D7991CAAB55A3D07B3ADC51284CE275CA504FB77", digest);
  }

  @Test
  public void bytes2_1024MaxChunk() throws Exception {
    byte[] bytes;
    try (InputStream inputStream =
        Objects.requireNonNull(FastHashTest.class.getResourceAsStream("/file2"))) {
      bytes = inputStream.readAllBytes();
    }

    FastHash fastHash = FastHash.getInstance("SHA1", 1024);
    String digest = fastHash.digest(new ByteArrayDataStream(bytes));

    assertEquals("1A077C215F12D21C6BF68F58C8A43EC4ADDC98C1", digest);
  }

  @Test
  public void messageDigestIsReset() throws InterruptedException, ExecutionException {

    ExecutorService executorService = Executors.newFixedThreadPool(2);

    FastHash fastHash = FastHash.getInstance("SHA1", 64 * 1024);

    Callable<String> c1 =
        () ->
            fastHash.digest(
                new FileDataStream(
                    new File(
                        Objects.requireNonNull(FastHashTest.class.getResource("/file1"))
                            .getPath())));

    Callable<String> c2 =
        () ->
            fastHash.digest(
                new FileDataStream(
                    new File(
                        Objects.requireNonNull(FastHashTest.class.getResource("/file2"))
                            .getPath())));

    List<Future<String>> futureList = executorService.invokeAll(List.of(c1, c2));

    assertEquals("D7991CAAB55A3D07B3ADC51284CE275CA504FB77", futureList.get(0).get());
    assertEquals("1358D3029AB20807E99CE900AB76A1A277FA19DF", futureList.get(1).get());
  }
}
