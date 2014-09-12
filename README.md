get\_block\_locations\_test
======================
Tests calling FileSystem#getFileBlockLocations.

Building
-------------------------------------------------------------
In order to build and run this test, you must put the HDFS and Hadoop-common
jar files into your classpath.  In the Hadoop install, these are found under
share/hadoop/common/ share/hadoop/hdfs/

TODO: use Ivy.

Running
-------------------------------------------------------------
You have to set some Java system properties when running the test.

Here is an example of how to run the test:

    ANT\_OPTS="-Dget.block.locations.path=hdfs://localhost/foo" ant compile jar run

You may also want libhadoop.so in your LD\_LIBRARY\_PATH.

Alternately, you can directly run the jar with:

    java -Dget.block.locations.path=hdfs://localhost/foo com.cloudera.GetBlockLocations

Contact information
-------------------------------------------------------------
Colin Patrick McCabe <cmccabe@alumni.cmu.edu>
