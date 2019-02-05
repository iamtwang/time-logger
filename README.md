# time-logger

- A typical method to monitor an operation
```java
    public void demo() {
        long start = System.currentTimeMillis();

        // do something...

        long end = System.currentTimeMillis();
        Logger.debug(end - start);
    }
```
## How to Use

- Add annotation @TimLogger to method

```java
    @TimeLogger
    public void demo() {
		//do something...
    }
```