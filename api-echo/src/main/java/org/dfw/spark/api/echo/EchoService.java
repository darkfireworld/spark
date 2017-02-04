package org.dfw.spark.api.echo;

import org.dfw.spark.core.support.dto.Dto;

/**
 * Test
 */
public interface EchoService {
    Dto<String> echo(String name);
}
