import res.TableSymbol
import res.Token
import java.io.File
import kotlin.system.exitProcess

fun main() {
    println("Enter filename to parse:")
    var tokenStream: MutableList<Token>
    var symbolTable: MutableMap<Int, TableSymbol>
    val fileName: String? = readLine()
    val file = File(fileName)
    var lines = 0
    var errors = 0
    if (!file.exists()) {
        println("$fileName does not exist.")
        exitProcess(-1)
    }
    val fp = FilePrinter()
    val la = LexicalAnalyzer(fp)

    println("$fileName found. Reading file...")
    println()
    val bufferedReader = file.bufferedReader()
    val text: List<String> = bufferedReader.readLines()
    loop@ for (line in text) {
        lines++
        try {
            var currentLine = line.toCharArray()
            for (char in currentLine) {
                la.process(char)
            }
            la.process('\n')
        } catch (e: Exception) {
            if(e.message == "comment") continue@loop
            errors++
            fp.addError("Error at line $lines: ${e.message}")
            continue@loop
        }
    }
    symbolTable = fp.symbolTable  //.get("Global")!! as MutableList<Identifier>
    tokenStream = fp.tokenStream
    tokenStream.add(Token("eof", ""))
    val ssa = SyntaxAnalyzer(tokenStream, symbolTable)
    var parseOrder = mutableListOf<Int>()
    try {
        parseOrder = ssa.parse()
        fp.symbolTable = ssa.symbolTable //as MutableMap<String, List<Identifier>>
    }catch (e: Exception){
        fp.addError(e.message!!)
        errors++
    }
    fp.makeOutputDir()
    fp.makeTokenFile()
    fp.makeParseFile(parseOrder)
    if(errors > 0) fp.makeErrorFile()
    fp.makeSymbolTableFile()
}
