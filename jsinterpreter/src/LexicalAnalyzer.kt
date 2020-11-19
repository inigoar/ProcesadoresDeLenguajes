class LexicalAnalyzer(fp: FilePrinter){

    private var state = 0
    private var token = ""
    private var genToken = fp
    private var level = 0
    private val uniCharTk = charArrayOf('+', '*', '%', '<', '!', '=', '(', ')', '{', '}', ';', ',')

    fun process(char: Char){
        when(state){
            0-> state0(char)
            1-> state1(char)
            8-> state8(char)
            9-> state9(char)
            11-> state11(char)
            12-> state12(char)
            13-> state13(char)
            16-> state16(char)
            17-> state17(char)
        }
    }

    private fun state0(char: Char){
        when{
            (uniCharTk.contains(char))-> {
                state = 1
                state1(char)
            }
            (char == '-')-> {
                state = 8
            }
            (char in '0'..'9')-> {
                state = 9
                state9(char)
            }
            (char == '\'' )-> {
                state = 11
                state11(char)
            }
            ((char in 'a' .. 'z') || (char in 'A' .. 'Z')) -> {
                state = 13
                state13(char)
            }
            (char == '/')-> {
                state = 16
                state16(char)
            }
            else-> state = 0
        }
    }

    private fun state1(char: Char){
        genToken.addToken(4, char.toString())
        state = 0
    }

    private fun state8(char: Char){
        if(char == '='){
            genToken.addToken(4, "-=")
        }else{
            genToken.addToken(4, "-")
        }
        state = 0
    }

    private fun state9(char: Char){
        when{
            (char in '0'..'9')-> {
                token += char
                state = 9
            }
            ((char in 'a'..'z') || (char in 'A'..'Z'))-> {
                token = ""
                state = 0
                throw Exception("Bad lexeme")
            }
            else-> {
                if(token.toInt() <= 32767) {
                    genToken.addToken(2, token)
                    token = ""
                    state = 0
                    state0(char)
                }
                else{
                    token = ""
                    state = 0
                    throw Exception("Bad lexeme")
                }
            }
        }
    }

    private fun state11(char: Char){
        state = 12
        token += char
    }

    private fun state12(char: Char){
        if(char != '\''){
            token += char
            state = 12
        }
        else{
            token += char
            if (token.length > 66) throw Exception("String too long")
            genToken.addToken(3, token)
            state = 0
            token = ""
        }
    }

    private fun state13(char: Char){
        if((char in 'a' .. 'z') || (char in 'A' .. 'Z') || (char == '_') || (char in '0' .. '9')){
            token += char
            state = 13
        }else{
            if(genToken.isKeyword(token)) {
                genToken.addToken(4, token) // identificadores/palabras reservadas
                state = 0
                token = ""
                state0(char)
            }else{
                genToken.addToken(5, token) // identificadores/palabras reservadas
                state = 0
                token = ""
                state0(char)
            }
        }
    }

    private fun state16(char: Char){
        when{
            (char == '/') -> {
                state = 17
                token = ""
            }
            (char in '0' .. '9') -> {
                genToken.addToken(4, "/")
                state = 9
                state9(char)
            }
            else -> {
                state = 0
                throw Exception("Bad lexeme")
            }
        }
    }

    private fun state17(char: Char){
        if(char == '\n'){
            state = 0
            genToken.addToken(1, token)
            token = ""
        }else {
            token += char
        }
    }
}
