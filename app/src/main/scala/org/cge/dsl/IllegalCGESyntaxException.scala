package org.cge.dsl

class IllegalCGESyntaxException extends Exception:
    def this(message: String) = this()
    def this(message: String, cause: Throwable) = this()
    def this(cause: Throwable) = this()
