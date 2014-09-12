/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudera;

import java.io.IOException;
import java.util.Arrays;
import java.lang.System;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

/**
 * This benchmark tests I/O in HDFS.
 */
public class GetBlockLocations {
  private static void usage(int retval) {
    System.err.println(
        "HioBench: tests random HDFS I/O.\n" +
        "\n" +
        "Java system properties to set:\n" +
        "get.block.locations.path [uri]     URI to get block locations for.\n"
    );
    System.exit(retval);
  }

  static String getStringOrDie(String key) {
    String val = System.getProperty(key);
    if (val == null) {
      System.err.println("You must set the string property " + key + "\n\n");
      usage(1);
    }
    return val;
  }

  public static void main(String[] args) throws Exception {
    final Configuration conf = new Configuration();
    String url = getStringOrDie("get.block.locations.path");
    final FileSystem fs = FileSystem.get(new URI(url), conf);

    if (!fs.exists(new Path(url))) {
      System.out.println("no file at " + url);
      System.exit(1);
    }
    BlockLocation locs[] = null;
    try {
      locs = fs.getFileBlockLocations(new Path(url), 0, Long.MAX_VALUE);
    } catch (IOException e) {
      System.out.println("Error calling getFileBlockLocations(" + url + ")\n");
      e.printStackTrace(System.err);
      System.exit(1);
    }

    String prefix = "";
    for (BlockLocation loc : locs) {
      System.out.println(prefix);
      System.out.println("{");
      System.out.println("  hosts =         " + Arrays.toString(loc.getHosts()));
      System.out.println("  cachedHosts =   " + Arrays.toString(loc.getCachedHosts()));
      System.out.println("  names    =      " + Arrays.toString(loc.getNames()));
      System.out.println("  topologyPaths = " + Arrays.toString(loc.getTopologyPaths()));
      System.out.println("  offset =        " + loc.getOffset());
      System.out.println("  length =        " + loc.getLength());
      System.out.println("  corrupt =       " + loc.isCorrupt());
      System.out.println("}");
      prefix = ",";
    }
  }
}
