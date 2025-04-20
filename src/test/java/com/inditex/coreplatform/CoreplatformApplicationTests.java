package com.inditex.coreplatform;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoreplatformApplicationTests {

  @Test
  void contextLoads() {
  }

  @Test
  void mainMethodRunsSuccessfully() {
    // Verifica que el método main se ejecuta sin lanzar excepciones
    assertDoesNotThrow(() -> CoreplatformApplication.main(new String[] {}));
  }

}
