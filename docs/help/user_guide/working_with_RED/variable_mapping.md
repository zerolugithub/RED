## Variable mapping - dealing with parameterized paths to libraries and
resources

Whenever parameterized file path is used in resources or libraries paths
(paths are resolved during Robot runtime when parameter in path is known), RED
will not be able to evaluate parameter value by itself.
![](variable_mapping/variable_mapping_5.png)

Variables mappings can be used to statically assign value to parameters in
paths to resolve such paths to resources and libraries.

Note

    Variables provided in red.xml are used only by RED for validation purpose and are not added to Robot run command line. During test execution variables have to be provided by Robot.

Open red.xml file, in Variables mappings assign static value for parameter in
path: ![](variable_mapping/variable_mapping_6.gif) When successful, path will
be recognized and validation will take place.
![](variable_mapping/variable_mapping_7.png) **Mind that Variable Mapping maps
value to already existing variable.** If need a variable which is global
(visible across whole Project) check [Variable Files](variable_files.md)
section. Such variables from Python files can also be used in Variable
Mapping.

[Return to Help index](http://nokia.github.io/RED/help/)
