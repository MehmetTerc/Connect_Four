/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package PIS_HU1;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {
  private GameEngine game = new GameEngine();

  // Es wird geschaut, ob das Board bei Spielbeginn leer ist
  @Test
  public void testEmptyBoard() {
    Assert.assertArrayEquals(
        "Das Spielfeld ist nicht leer, obwohl das Spiel neu gestartet wurde",
        game.getPlayerBoard(),
        new long[] {0L, 0L});
  }

  // Es wird geprüft, ob der Zug korrekt ausgeführt worden ist und dieser richtig gespeichert wird
  @Test
  public void testMove() {
    game.reset();
    game.makeMove(0);
    Assert.assertEquals(
        "Der Zug wurde nicht richtig gespeichert",
        "1",
        Long.toBinaryString(game.getPlayerBoard()[0]));
  }

  // es wird mehrere Züge geprüft (Warum geht es nicht mit noch mehr zügen)
  @Test
  public void testMoreMove() {
    game.reset();
    game.makeMove(0);
    game.makeMove(2);
    game.makeMove(4);
    game.makeMove(1);
    game.makeMove(2);
    game.makeMove(0);
    Assert.assertEquals(
        "Die Zuege wurden nicht korrekt gespeichert",
        "10000000000001000000000000001",
        Long.toBinaryString(game.getPlayerBoard()[0]));
  }

  // Es wird geprüft, ob der andere Spieler nach einem Zug dran ist
  @Test
  public void testPlayerTurn() {
    game.reset();
    game.makeMove(5);
    // Durch die logische Verundung mit getCount() kommt immer eine 0 oder 1, je nachdem welcher
    // Spieler dran ist
    Assert.assertEquals("Spielerzug nicht richtig gewechselt", 1, (game.getCount() & 1));
  }

  // es wird geprüft, ob eine Zeile schon voll ist
  @Test
  public void testFullCol() {
    game.reset();
    game.makeMove(0);
    game.makeMove(0);
    game.makeMove(0);
    game.makeMove(0);
    game.makeMove(0);
    game.makeMove(0);
    game.makeMove(0);
    Assert.assertFalse(
        "Die Spalte ist voll, trotzdem kann ein Stein gesetzt werden", game.isPlayable(0));
  }

  // Es wird ein Beispiel für einen horizontalen Sieg im Board gespeichert und dann die isWin
  // Methode für eine horizontale Viererlinien getestet
  @Test
  public void testHorizontalWin() {
    // 1000000100000010000001 horizontal win
    game.getPlayerBoard()[0] = Long.parseLong("1000000100000010000001", 2);
    Assert.assertTrue("Es gibt vier horizontale Steine in Reihe,isWin erkennt dies nicht", game.isWin(game.getPlayerBoard()[0]));
  }

  // Es wird ein Beispiel für einen diagonalen Sieg im Board gespeichert und dann die isWin Methode
  // für eine diagonale Viererlinien getestet
  @Test
  public void testDiagonalWin() {
    game.reset();
    // Beispiel für eine Bitkombination, wenn vier gelbe Steine diagonal in Reihe stehen
    game.getPlayerBoard()[0] = Long.parseLong("1000000111000000100000001", 2);
    Assert.assertTrue(
        "Es gibt vier diagonale Steine in Reihe,isWin erkennt dies nicht",
        game.isWin(game.getPlayerBoard()[0]));
  }

  // Es wird ein Beispiel für einen vertikalen Sieg im Board gespeichert und dann die isWin
  // für eine vertikale Viererlinien getestet
  @Test
  public void testVerticalWin() {
    game.reset();
    game.getPlayerBoard()[0] = Long.parseLong("1111", 2);
    Assert.assertTrue(
            "Es gibt vier vertikale Steine in Reihe,isWin erkennt dies nicht",
            game.isWin(game.getPlayerBoard()[0]));
  }

  // Es werden verschiedene Züge gespielt, die keinen Sieg ergeben und dann wird die isWin Methode
  // geprüft, ob sie das erkennt
  @Test
  public void testIsNotAWin() {
    game.reset();
    game.makeMove(6);
    game.makeMove(2);
    game.makeMove(1);
    game.makeMove(5);
    game.makeMove(4);
    Assert.assertFalse(
        "Es gibt keine 4 Steine in Reihe, isWin ist fehlerhaft",
        game.isWin(game.getPlayerBoard()[1] | game.getPlayerBoard()[0]));
  }

  // Es wird geprüft, ob undoMove den Zug korrekt zurücknimmt
  @Test
  public void testUndoMove() {
    game.makeMove(1);
    game.undoMove();
    Assert.assertEquals("Der Zug wurde nicht korrekt zurückgenommen",0L, game.getPlayerBoard()[0]);
  }

  // Es wird geprüft, ob isPlayable() für diese beispielhafte Eingabe den korrekten Wert ausgibt
  @Test
  public void isPlayable() {
    Assert.assertTrue(
        "Der Zug ist eigentlich spielbar, aber es wird nicht erkannt", game.isPlayable(2));
  }

  //Es wird geprüft, ob isLegal() korrekt funktioniert
  @Test
  public void testIsLegal() {
    game.reset();
    //Simulation der vollen Spalte 5
    int i=0;
    while(i<6){
      game.makeMove(4);
      i++;
    }
    Assert.assertFalse(game.isLegal(game.getPlayerBoard()[0] | (1L << game.getHeightCol()[4])));

  }

  // Es wird geprüft, ob das Spiel korrekt zurückgesetzt wird
  @Test
  public void testReset() {
    game.makeMove(1);
    game.makeMove(5);
    game.reset();
    Assert.assertEquals(
        "Das Board wurde nicht korrekt zurückgesetzt", 0L, game.getPlayerBoard()[0]);
    Assert.assertEquals(
        "Das Board wurde nicht korrekt zurückgesetzt", 0L, game.getPlayerBoard()[1]);
  }

  // Es wird geprüft, ob listMoves überhaupt
  @Test
  public void testListMove() {
    game.reset();
    game.makeMove(1);
    Assert.assertNotEquals("Es werden keine möglichen Moves angezeigt,obwohl sie möglich wären",null, game.listMoves());
  }

  // Prüft, ob die Methode simulatePlays auch wirklich Spiele simuliert
  @Test
  public void testSimulatePlays() {
    int[] i = {0, 0, 0};
    Assert.assertNotEquals("Es wurden keine Spiele simuliert", i, game.simulatePlays(game, 100));
  }

  /*Es wird ein Spiel simuliert, wo drei gelbe Steine, in der ersten Spalte, in einer Reihe sind.
  chooseBestMove muss jetzt erkennen, dass der nächstbeste Zug die Spalte 1 ist.
   */
  @Test
  public void testChooseBestMove() {
    game.reset();
    game.makeMove(0);
    game.makeMove(1);
    game.makeMove(0);
    game.makeMove(1);
    game.makeMove(0);
    Assert.assertEquals("Der beste Zug wurde falsch ausgewählt", 0,game.chooseBestMove(game, 100));
  }

  // Es wird geprüft, ob das Spiel unentschieden ausgegangen ist
  @Test
  public void testDraw() {
    game.reset();
    int[] drawCol = {6, 13, 20, 27, 34, 41, 48};
    game.setHeightCol(
        drawCol); // heightCol prüft, ob man noch ein Stein in die Zeile setzen kann und wird hier
                  // auf den vollen Wert für alle Spalten gesetzt
    Assert.assertTrue(
        "Das Board ist voll und es dürfte kein Zug mehr möglich sein",
        game.listMoves().isEmpty()); // listMoves gibt die nächsten möglichen Züge aus
  }
}
