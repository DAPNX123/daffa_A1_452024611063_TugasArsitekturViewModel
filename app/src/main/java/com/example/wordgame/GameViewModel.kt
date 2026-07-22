package com.example.wordgame // Ganti dengan package aplikasi Anda

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        getNextWord()
    }

    /*
     * Mengambil kata acak, mengacak hurufnya, dan memastikannya tidak sama dengan aslinya.
     */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        // Terus acak jika kata yang diacak kebetulan sama persis dengan kata asli
        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }

        // Cek apakah kata sudah pernah dimainkan di sesi ini
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            _currentWordCount++
            wordsList.add(currentWord)
        }
    }

    /*
     * Lanjut ke kata berikutnya jika masih di bawah batas maksimal (10 kata).
     */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else {
            false
        }
    }

    /*
     * Mengecek jawaban pemain, jika benar tambah skor.
     */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, ignoreCase = true)) {
            _score += SCORE_INCREASE
            return true
        }
        return false
    }

    /*
     * Reset data jika pemain menekan "Play Again".
     */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
}