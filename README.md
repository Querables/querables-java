# querables-java
Data structures for Java allowing complex queries with high performance. ![alt img](https://travis-ci.org/Querables/querables-java.svg?branch=master)

## Description
`QMap` interface allows you to use `get()` method with wildcards (currently expressed with `null` values) as key's fields.

## Usage
```java
class Person {
  private String name;
  private String surname;
}

QMap<Person, Integer> debts = new QuerableMap<>(Person.class);

debts.put(new Person("John", "Cena"), 100);  // John Cena borrowed $100
debts.put(new Person("Eve", "Smith"), 50);   // Eve Smith borrowed $50
debts.put(new Person("Mark", "Cena"), 30);   // Mark Cena borrowed $30

// How much has John Cena borrowed?
debts.get(new Person("John", "Cena"));       // result: [100]

// How much the whole Cena family borrowed?
debts.get(new Person(null, "Cena"));         // result: [100, 30]
```

## Caveats
At the moment, only the `QuerableMap` implementation is available which is not thread-safe.

## Contribution
All contributions via pull requests are welcome. If you like to start working on a feature or a bug, please create an issue to give a headsup to avoid duplicated efforts.
