/**
 * Basic Cryptanalysis Java Solution (https://www.hackerrank.com/challenges/basic-cryptanalysis). 
 * As far, as I had limited amount of time - to find solution, this code is not very nice (some refactoring is needed).
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Solution {

    public static void main( String[] args ) throws Exception { 

        Decoder decoder = initializeDecoder( new BufferedReader(new FileReader("dictionary.lst")) );

        BufferedReader br = new BufferedReader( new InputStreamReader( System.in ) );
        String s;
        String sb = "";
        while ( ( s = br.readLine() ) != null ) {
            sb += s;
        }
        System.out.println( decoder.decode( sb ) );
    }

    private static Decoder initializeDecoder( BufferedReader br ) throws IOException {
        List<String> dictionary = new ArrayList<String>();

        String s;
        while ( ( s = br.readLine() ) != null ) {
            dictionary.add( s.toLowerCase() );
            //System.out.println(s);
        }

        Decoder decoder = new Decoder( dictionary );
        return decoder;
    }

    private static class Decoder {

        private Map<Integer, List<String>> lengthToWords = new HashMap<Integer, List<String>>();

        public Decoder( List<String> dictionary ) {
            for ( String word : dictionary ) {
                int wordLength = word.length();
                List<String> words = this.lengthToWords.get( wordLength );
                if ( words == null ) {
                    words = new ArrayList<String>();
                    this.lengthToWords.put( wordLength, words );
                }
                words.add( word );
            }
        }

        private List<String> getCandidates( int length ) {
            List<String> candidates = this.lengthToWords.get( length );
            return candidates;
        }

        private boolean crackCipher( List<String> encodedWords, Map<Character, Character> cipher, Set<Character> usedDecodeCharacters ) {
            
            //System.out.println(encodedWords.get( 0 ));
            //System.out.println(cipher);

            if ( encodedWords.size() == 0 ) {
                return true;
            }

            String firstEncodedWord = encodedWords.get( 0 ).toLowerCase();

            int wordLength = firstEncodedWord.length();

            List<String> candidates = this.getCandidates( wordLength );

            Set<Character> resolvedEncodedCharacters = new HashSet<Character>();

            CANDIDATES_LOOP: for ( String candidate : candidates ) {

                resolvedEncodedCharacters.clear();

                for ( int i = 0; i < wordLength; i++ ) {

                    Character encodedChar = firstEncodedWord.charAt( i );
                    Character candidateChar = candidate.charAt( i );

                    Character cipherChar = cipher.get( encodedChar );

                    boolean candidateCharIsUsed = usedDecodeCharacters.contains( candidateChar );

                    if ( candidateChar.equals( cipherChar ) ) {
                        continue;
                    }

                    if ( ( cipherChar == null ) && ( !candidateCharIsUsed ) ) {
                        cipher.put( encodedChar, candidateChar );
                        resolvedEncodedCharacters.add( encodedChar );
                        usedDecodeCharacters.add( candidateChar );
                        continue;
                    }

                    for ( Character addedChar : resolvedEncodedCharacters ) {
                        Character decodeChar = cipher.remove( addedChar );
                        usedDecodeCharacters.remove( decodeChar );
                    }
                    continue CANDIDATES_LOOP;
                }

                if ( this.crackCipher( encodedWords.subList( 1, encodedWords.size() ), cipher, usedDecodeCharacters ) ) {
                    return true;
                }

                for ( Character addedChar : resolvedEncodedCharacters ) {
                    Character decodeChar = cipher.remove( addedChar );
                    usedDecodeCharacters.remove( decodeChar );
                }
            }

            return false;
        }

        private List<String> decode( List<String> encodedWords ) {
            Map<Character, Character> cipher = new HashMap<Character, Character>();

            this.crackCipher( encodedWords, cipher, new HashSet<Character>() );

            List<String> result = new ArrayList<String>();

            StringBuilder stringBuilder = new StringBuilder();

            for ( String encodedWord : encodedWords ) {
                int wordLength = encodedWord.length();

                for ( int i = 0; i < wordLength; i++ ) {
                    char encodedChar = encodedWord.charAt( i );
                    Character decodedChar = cipher.get( encodedChar );
                    stringBuilder.append( decodedChar );
                }

                result.add( stringBuilder.toString() );
                stringBuilder.setLength( 0 );
            }

            return result;
        }

        public String decode( String phrase ) {
            String[] parts = phrase.split( "\\s+" );
            List<String> decodedWords = this.decode( Arrays.asList( parts ) );
            StringBuilder stringBuilder = new StringBuilder();
            for ( String decodedWord : decodedWords ) {
                stringBuilder.append( decodedWord ).append( " " );
            }
            // remove space in the tail
            stringBuilder.setLength( stringBuilder.length() - 1 );
            return stringBuilder.toString();
        }
    }
}
