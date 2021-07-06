<!---
![CI](https://github.com/popoppo/clojure-fire/workflows/CI/badge.svg)
-->

# clojure-spinners

![all-spinners](https://user-images.githubusercontent.com/934188/124562620-34e91900-de7a-11eb-825e-85ec252ba4f5.gif)

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

You can find some more examples [here](https://github.com/popoppo/clojure-spinners/tree/dev/src/clojure_spinners/examples).

## Related
- [cli-spinners](https://github.com/sindresorhus/cli-spinners) - Spinners for use in the terminal.
- [ora](https://github.com/sindresorhus/ora) - Elegant terminal spinners in JavaScript.
- [halo](https://github.com/manrajgrover/halo) - Elegant terminal spinner in Python.
- [spinner](https://github.com/clj-commons/spinner) - Simple ANSI text spinner for command line Clojure apps.
