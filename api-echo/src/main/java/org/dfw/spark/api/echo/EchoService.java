package org.dfw.spark.api.echo;

import org.dfw.spark.core.dto.Dto;

/**
 * Test
 */
public interface EchoService {
    Dto<String> echo(String name);
}
