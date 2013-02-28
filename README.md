preferd
=======

Java Preferences CLI

#### Usage

```
Usage: preferd <command> <arguments>
Commands:
    ls/list    [NODE [KEY]]
    get        NODE KEY
    put/set    NODE [KEY VALUE]
    rm/remove  NODE [KEY]

    cp/copy    NODE1 [KEY1] NODE2
               NODE1 KEY1 [NODE2] KEY2

    mv/move    NODE1 [KEY1] NODE2
               NODE1 KEY1 [NODE2] KEY2

    export     NODE [--prop] [-r]

    import     --prop [NODE] < PROP_FILE
    import     < XML_FILE

    help

Arguments:
    NODE in "user" namespace       -u /node/path
    NODE in "system" namespace     -s /node/path
    KEY                            -k key
    VALUE                          -v value
    PROP_FILE                      Java Properties file
    XML_FILE                       Java Preferences exported XML file
```


#### Installation

```
mkdir preferd
cd preferd
git clone git@github.com:shakiba/preferd.git .
mvn package
sudo touch /usr/bin/preferd
sudo echo "./target/appassembler/bin/preferd \"\$@\"" > /usr/bin/preferd
sudo chmod a+x /usr/bin/preferd
sudo chmod a+x ./target/appassembler/bin/preferd
```