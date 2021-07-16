![Test](https://github.com/popoppo/clojure-spinners/workflows/RunExamples/badge.svg)

# clojure-spinners

<img src="https://user-images.githubusercontent.com/934188/125885511-a3395bab-1fcc-413b-9f82-60f3a749c843.gif" width="480px">

Various spinners for Clojure.  
The built-in spinners are coming from [cli-spinners](https://github.com/sindresorhus/cli-spinners) and the implementation is designed to be (somewhat) compatible with [clj-commons/spinner](https://github.com/clj-commons/spinner)

## Usage

The following 3 steps are needed to run spinners.
 - create a spinner instance by `create!`
 - start the spinner by `start!`
 - stop it by `stop!`

Here is a simple example.

```clojure
(let [opts {:spinner :hearts
            :text "Hearts!!"}
      s (create! opts)]
  (start! s)
  (Thread/sleep 2000)
  (stop! s))
```

That can also be done with `spin!`.

```clojure
(spin! {:text "Spin with spin!"}
       (Thread/sleep 2000))
```

You can find more examples [here](https://github.com/popoppo/clojure-spinners/tree/dev/src/clojure_spinners/examples).

<!-- If you want to make spinners colorful,  -->

## Character width
You can use any unicode chars.  
The width of them are calculated based on the following document.

https://www.unicode.org/Public/UCD/latest/ucd/EastAsianWidth.txt  
(Top page: https://www.unicode.org/reports/tr11/)

Note that all chars defined as "Ambiguous" are handled as half width characters.  
Basically you don't need to care about it, but might get unexpected output with them.

## Related
- [cli-spinners](https://github.com/sindresorhus/cli-spinners) - Spinners for use in the terminal.
- [ora](https://github.com/sindresorhus/ora) - Elegant terminal spinners in JavaScript.
- [halo](https://github.com/manrajgrover/halo) - Elegant terminal spinner in Python.
- [spinner](https://github.com/clj-commons/spinner) - Simple ANSI text spinner for command line Clojure apps.

I'd like to thank all related works!!
