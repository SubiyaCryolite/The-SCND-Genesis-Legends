# JVM source header

Every new JVM source in this project — `.java`, `.kt`, and `.kts` (except third-party retained headers such as `Audio.java`) — must start with the project GPL banner **before** the `package` declaration (or at the top of the file when there is no package, e.g. Gradle scripts).

Formatting: put `package` (or the first `import` / top-level statement) on the **next line** immediately after the closing `*/` of the banner — one newline, no blank line, never glued on the same line.

Copy exactly from `.idea/fileTemplates/includes/File Header.java`, or use this block:

````java
/**************************************************************************

 The SCND Genesis: Legends is a fighting game based on THE SCND GENESIS,
 a webcomic created by Ifunga Ndana ((([<a href="https://www.scndgen.com">https://www.scndgen.com</a>]))).

 The SCND Genesis: Legends RMX  © 2017 Ifunga Ndana.

 The SCND Genesis: Legends is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 The SCND Genesis: Legends is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with The SCND Genesis: Legends. If not, see <<a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>>.

 **************************************************************************/
package com.example;

import java.util.Objects;
````

Do not invent a shorter or alternate license header. IntelliJ/Cursor templates and the Copyright profile also inject this text for new classes.

This banner licenses **source code**, not artwork or story. Assets are CC BY-NC 3.0 — see [licensing.md](licensing.md) and [`LICENSE.md`](../../LICENSE.md).
