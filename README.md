# LINQ for Java
a set of linq extensions for Java to ease list query and manipulation
## Installation
You can add the LINQ Maven project to your NetBeans and add it as a dependency to your Maven project
## Usage
```java
// a mock class
public class Demo {

    private String first;
    public String second;

    public Demo(String first, String second) {
        this.first = first;
        this.second = second;
    }
}

// a second mock class

public class DemoAgain {

    public String third;
    public String fourth;

    public DemoAgain(String third, String fourth) {
        this.third = third;
        this.fourth = fourth;
    }
}

ArrayList<Demo> demos = new ArrayList<>();
demos.add(new Demo("a", "b"));
demos.add(new Demo("a1", "b1"));
demos.add(new Demo("a2", "b2"));
demos.add(new Demo("a", "b3"));
demos.add(new Demo("a", "b4"));
demos.add(new Demo("a", "b5"));

try {
    // check if list has any item
    boolean res1 = new Q<Demo>(demos).any();

    // check if list has any object which its property "first" has a value of "a"
    boolean res2 = new Q<Demo>(demos).any("first", "a");

    // check if list has any object which its property "first" has a value of "a", then check if the result list from previous query with property "second" has value "b4" 
    boolean res3 = new Q<Demo>(demos).where("first", "a").where("second", "b4").any();

    HashMap<String, Object> properties = new HashMap<>();
    properties.put("first", "a");
    properties.put("second", "b4");

    // get items where it has property "first" = value "a", and property "second" = "b"
    AbstractList<Demo> res4 = new Q<Demo>(demos).where(properties).toList();

    // get items where a custom rule is applied via a callback
    AbstractList<Demo> res5
            = new Q<Demo>(demos).where(new QCompare<Demo>() {
                @Override
                public boolean is(Demo value) {
                    return value.second.contains("1");
                }
            }).toList();

    // get items where it contains an object like the one supplied (equal in values, not in reference) 
    boolean res6 = new Q<Demo>(demos).contains(new Demo("a2", "b2"));

    // get items where it has property "first" = value "a", and property "second" = "b"
    boolean res8 = new Q<Demo>(demos).where("first", "a").contains(new Demo("a", "b4"));

    // get all items where property "first" = "a", then get the last item
    Demo res9 = new Q<Demo>(demos).where("first", "a").last();

    // transform all items into another list of type DemoSecond
    Q<DemoAgain> res10
            = new Q<Demo>(demos).select(new QSelect<Demo, DemoAgain>() {
                @Override
                public DemoAgain select(Demo o) {
                    return new DemoAgain(o.first + " + new!!", o.second + " + new2!!");
                }
            });

} catch (ReflectionOperationException ex) {
} catch (IllegalArgumentException ex) {
}


```
## Contributing
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request
## History
TODO: Write history
## Credits
TODO: Write credits
## License
TODO: Write license
