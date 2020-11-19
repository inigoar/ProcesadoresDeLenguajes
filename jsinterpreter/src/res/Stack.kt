package res

class Stack {
    var elements: MutableList<Any> = mutableListOf()

    fun isEmpty() = elements.isEmpty()
    fun size() = elements.size
    fun push(item: Any) = elements.add(item)
    fun pop(): Any? {
        val item = elements.lastOrNull()
        if (!isEmpty()) elements.removeAt(elements.size - 1)
        return item
    }

    fun peek(): Any? = elements.lastOrNull()
    fun elementAt(index: Int): Any? = elements[elements.size-index-1]
    fun replaceAt(index: Int, item: Pair<*, *>) = elements.set(elements.size-index-1, item)
}