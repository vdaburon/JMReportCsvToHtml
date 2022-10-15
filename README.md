# Generating an HTML table from a csv file
This program reads a csv file and generates an html table div block (not a complet html page)

Reads a csv file and generates an html div block as an html table with an embedded stylesheet.

The first column is left-aligned, the other columns right-aligned

Odd lines are gray, even lines are white

The input csv file comes from a "Save Table Data"" of a **JMeter Report** (Synthesis Report, Aggregate Report, Summary Report) or from jmeter-graph-tool-maven-plugin

It is preferable to sort the lines (except the header) before generating the table.

The generated HTML table can be directly included in an HTML page with the GenereHtmlForDirectory tool.

## License
See the LICENSE file Apache 2 [https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0)

## Html table generated
Some table generated with this plugin

The synthesis csv file **input** first argument
![synthesis csv file](doc/images/example_csv_file.png)

The html table **output** second argument

![synthesis table_html](doc/images/example_csv_file_to_html.png)

## Usage

The maven groupId, artifactId and version, this plugin is in the **Maven Central Repository**

```xml
<groupId>io.github.vdaburon</groupId>
<artifactId>csv-report-to-html</artifactId>
<version>1.0</version>
```
Just include the plugin in your `pom.xml` and execute `mvn verify`.

```xml
<project>
    <!-- ... -->
    <dependencies>
        <dependency>
            <groupId>io.github.vdaburon</groupId>
            <artifactId>csv-report-to-html</artifactId>
            <version>1.0</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>1.2.1</version>
                <executions>
                    <execution>
                        <id>aggregate_csv_to_html</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>java</goal>
                        </goals>
                        <configuration>
                            <mainClass>io.github.vdaburon.jmeter.utils.ReportCsv2Html</mainClass>
                            <arguments>
                                <argument>${project.build.directory}/jmeter/results/AggregateReport.csv</argument>
                                <argument>${project.build.directory}/jmeter/results/AggregateReport.html</argument>
                            </arguments>
                        </configuration>
                    </execution>
                    <execution>
                        <id>synthesis_csv_to_html</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>java</goal>
                        </goals>
                        <configuration>
                            <mainClass>io.github.vdaburon.jmeter.utils.ReportCsv2Html</mainClass>
                            <arguments>
                                <argument>${project.build.directory}/jmeter/results/SynthesisReport.csv</argument>
                                <argument>${project.build.directory}/jmeter/results/SynthesisReport.html</argument>
                            </arguments>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
```
## Link to others projects
Usally this plugin is use with [jmeter-graph-tool-maven-plugin](https://github.com/vdaburon/jmeter-graph-tool-maven-plugin)

The **jmeter-graph-tool-maven-plugin** create the report csv file and **this plugin** create the **html table report** from the csv file.

And an html page to display all graphs and html report tables could be generated whith [create-html-for-files-in-directory](https://github.com/vdaburon/CreateHtmlForFilesInDirectory)

