# DuoText

DuoText is a simple app designed to help in simple tasks in
which one may need to keep two texts under control. These
topics include, for example:

- translations;
- adaptations;
- comparisons.

It's true that most people interested in these topics have
more adequate tools for them. However, sometimes one only
needs the most basic tools, and DuoText is suited for those
cases. It's also my way to learn Java programming and
software development using Swing and AWT.

## Features

#### Double text typing

> What do you mean by "*double* text editor"?

DuoText has two text areas in which you can type in, both
disposed side by side, separated by a splitting bar. You can
slide this bar along the window to control the size of each
box or even collapse one of them.

#### Common text IO functions

> Does it support my good old ```txt``` files?

DuoText is able to do the basic functions of creating new,
opening and saving plain text files. That is, it can handle
those ```txt``` or any other non-formatted text file like
```c```, ```cpp```, ```py```, etc. Just don't try to open a
```pdf``` file for example; things can get a little *weird*.

#### DuoText document IO functions

> But can't I just open two Notepads?

The program also implements its own extension: the DuoText
document (using the extension ```dtxt```). It is to DuoText
what a ```txt``` file is to Notepad; it contains both the
text areas you would use in DuoText, so there's no need to
save two separate text files and load both individually, and
it costs only one extra byte to glue the texts into one file.

## License

This project is licensed under the terms of the MIT License.