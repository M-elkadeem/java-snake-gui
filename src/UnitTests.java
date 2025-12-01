import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.Color;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

public class UnitTests {

    // players tests
    @Test
    public void testConstructorSetsScoreAndIsPlaying() {
        player p = new player(10, true);

        assertEquals(10, p.getScore());
        assertTrue(p.isIsplaying());
    }

    @Test
    public void testSetName() {
        player p = new player(0, false);
        p.setName("Ali");

        assertEquals("Ali", p.getName());
    }

    @Test
    public void testSetID() {
        player p = new player(0, false);
        p.setID(5);

        assertEquals(5, p.getID());
    }

    @Test
    public void testUpdateScoreAddsValue() {
        player p = new player(10, false);
        p.update_score(7);

        assertEquals(17, p.getScore());
    }

    @Test
    public void testSetScoreReplacesValue() {
        player p = new player(10, false);
        p.setScore(99);

        assertEquals(99, p.getScore());
    }

    @Test
    public void testSetIsPlaying() {
        player p = new player(0, false);
        p.setIsplaying(true);

        assertTrue(p.isIsplaying());
    }


    // next is testing food class
    @Test
    public void testNormalFoodValues() {
        Normalfood f = new Normalfood(Color.GREEN, 1);

        assertEquals(Color.GREEN, f.getColor());
        assertEquals(1, f.getPointvalue());
        assertEquals("Normal", f.gettype());
    }
    @Test
    public void testPoisonFoodConstructor() {
        Poisonfood f = new Poisonfood(Color.RED, -1);

        assertEquals(Color.RED, f.getColor());
        assertEquals(-1, f.getPointvalue());
        assertEquals("Poison", f.gettype());
    }

    @Test
    public void testGoldenFoodConstructor() {
        Goldenfood f = new Goldenfood(Color.YELLOW, 3);

        assertEquals(Color.YELLOW, f.getColor());
        assertEquals(3, f.getPointvalue());
        assertEquals("Golden", f.gettype());
    }

    @Test
    public void testSetAndGetPosition() {
        Normalfood f = new Normalfood(Color.GREEN, 1);

        point p = new point(4, 7);
        f.setPosition(p);

        assertEquals(4, f.getPosition().getX());
        assertEquals(7, f.getPosition().getY());
    }



    // snake tests
    @Test
    public void testSnakeInitialization() {
        snake s = new snake(100, 100, 10);
        assertEquals(1, s.getlength());//cuz there is the head only
        assertNotNull(s.gethead());
    }
    @Test
    public void testGetHeadReturnsFirstSegment() {
        snake s = new snake(100, 100, 10);
        assertEquals(s.getBody().getFirst(), s.gethead());// cuz there is only one head , so this will be the snake body
    }

    @Test
    public void testMoveUp() {
        snake s = new snake(100, 100, 10);
        point oldHead = s.gethead();

        s.move(Direction.UP);

        point newHead = s.gethead();
        assertEquals(oldHead.getX(), newHead.getX());
        assertEquals(oldHead.getY() - 1, newHead.getY());// cuz when u go up , y will increase and x is fixed
    }
    // in the next i will test the left , directions >> down >> ( y will decrease and x is fixed  ) and for the right and the left >> y will be fixed and x will change accroding to the driection
    @Test
    public void testMoveDown() {
        snake s = new snake(100, 100, 10);
        point oldHead = s.gethead();

        s.move(Direction.DOWN);

        point newHead = s.gethead();
        assertEquals(oldHead.getX(), newHead.getX());
        assertEquals(oldHead.getY() + 1, newHead.getY());
    }

    @Test
    public void testMoveLeft() {
        snake s = new snake(100, 100, 10);
        point oldHead = s.gethead();

        s.move(Direction.LEFT);

        point newHead = s.gethead();
        assertEquals(oldHead.getX() - 1, newHead.getX());
        assertEquals(oldHead.getY(), newHead.getY());
    }

    @Test
    public void testMoveRight() {
        snake s = new snake(100, 100, 10);
        point oldHead = s.gethead();

        s.move(Direction.RIGHT);

        point newHead = s.gethead();
        assertEquals(oldHead.getX() + 1, newHead.getX());
        assertEquals(oldHead.getY(), newHead.getY());
    }

    @Test
    public void testMoveDoesNotChangeLength() {// here to make sure , that when the snake moves , the length is fixed , and can only change when it eats
        snake s = new snake(100, 100, 10);
        int oldLength = s.getlength();

        s.move(Direction.RIGHT);
        assertEquals(oldLength, s.getlength());
        s.move(Direction.UP);
        assertEquals(oldLength, s.getlength());
        s.move(Direction.LEFT);
        assertEquals(oldLength, s.getlength());
        s.move(Direction.DOWN);
        assertEquals(oldLength, s.getlength());
    }

    // the next test is to check the snake length when it east a specfic fruit ,

    @Test
    public void testGrowNormalFood() {
        snake s = new snake(100, 100, 10);

        Food normal = new Normalfood(Color.GREEN, 1);
        int oldLength = s.getlength();

        s.grow(normal);
// here it must only grow by one segement as we gave it 1 point value above
        assertEquals(oldLength + 1, s.getlength());
    }

    @Test
    public void testGrowGoldenFood() {
        snake s = new snake(100, 100, 10);

        Food golden = new Goldenfood(Color.YELLOW, 3);
        int oldLength = s.getlength();

        s.grow(golden);
// here it must only grow by 3 segement as we gave it 3 point value above
        assertEquals(oldLength + 3, s.getlength());
    }

    @Test
    public void testGrowPoisonFoodDoesNotGrow() {
        snake s = new snake(100, 100, 10);

        Food poison = new Poisonfood(Color.RED, -1);
        int oldLength = s.getlength();

        s.grow(poison);// here it will not make any affect , cuz in the grow function , we are checking the type , when it can grow afterwords , and here it can only grow when it's golden or normal

        assertEquals(oldLength, s.getlength());
    }

    @Test
    public void testShrinkWithPoisonFood() {

        // here we will just test the shrinking method when it eats a poison food
        snake s = new snake(100, 100, 10);

        // Grow the snake first so we can shrink it
        s.grow(new Normalfood(Color.GREEN, 1));
        s.grow(new Normalfood(Color.GREEN, 1));

        int oldLength = s.getlength();

        Food poison = new Poisonfood(Color.RED, -1);
        s.shrinking(poison);

        assertEquals(oldLength - 1, s.getlength());
    }

    @Test
    public void testShrinkCannotGoBelowOne() {
        snake s = new snake(100, 100, 10);

        // in the shrinking method if the deduction will make the snake length below one segment >> then we should that and make the snake length only one segment ( head one )
        Food strongPoison = new Poisonfood(Color.RED, -999);
        s.shrinking(strongPoison);

        assertEquals(1, s.getlength());
    }

    // board tests

    @Test
    public void testBoardInitialization() {
        Board board = new Board(200, 200);

        assertEquals(200, board.getBoardWidth());
        assertEquals(200, board.getBoardHeight());
        assertEquals(20, board.getTilesize());

        assertNotNull(board.getSnk());
        assertNotNull(board.getPerson());
        assertNotNull(board.getFoods());
    }

    @Test
    public void testBoardStartsWithNormalFood() {
        Board board = new Board(200, 200);

        // At least one normal food must exist
        boolean hasNormal = board.getFoods()
                .stream()
                .anyMatch(f -> f.gettype().equals("Normal"));

        assertTrue(hasNormal, "Board should start with at least one Normal food.");
    }

    @Test
    public void testAddFood() {
        Board board = new Board(200, 200);

        int oldSize = board.getFoods().size();

        Food f = new Normalfood(Color.GREEN, 1);
        board.getFoods().add(f);

        assertEquals(oldSize + 1, board.getFoods().size());
    }

    @Test
    public void testRemoveFood() {
        Board board = new Board(200, 200);

        Food f = new Normalfood(Color.GREEN, 1);
        f.setPosition(new point(2, 2));
        board.getFoods().add(f);

        int oldSize = board.getFoods().size();
        board.getFoods().remove(f);

        assertEquals(oldSize - 1, board.getFoods().size());
    }

    @Test
    public void testFoodPositionInvalidDueToSnake() {
        Board board = new Board(200, 200);

        // Get snake head
        point head = board.getSnk().gethead();

        // Trying to place food on the snake head
        assertTrue(board.isFoodPositionInvalid(new point(head.getX(), head.getY())));
    }

    @Test
    public void testFoodPositionInvalidDueToOtherFood() {
        Board board = new Board(200, 200);

        Food f = new Normalfood(Color.GREEN, 1);
        f.setPosition(new point(4, 4));

        board.getFoods().add(f);

        assertTrue(board.isFoodPositionInvalid(new point(4, 4)));
    }

    @Test
    public void testFoodPositionValid() {
        Board board = new Board(200, 200);

        // Choose a position far away from snake head and foods
        point p = new point(10, 10);

        assertFalse(board.isFoodPositionInvalid(p));
    }

    @Test
    public void testEatingFoodIncreasesScoreAndRemovesFood() {
        Board board = new Board(200, 200);

        Food f = new Normalfood(Color.GREEN, 1);// Normal food
        f.setPosition(new point(3, 3));

        board.getFoods().add(f);

        int oldScore = board.getPerson().getScore();
        int oldSize = board.getFoods().size();

        board.eaatingfood(f);

        assertEquals(oldScore + 1, board.getPerson().getScore());
        assertEquals(oldSize - 1 , board.getFoods().size());
    }

    @Test
    public void testCreatingNormalFoodIsNotAllowedWhenOneExists() {
        Board board = new Board(200, 200);

        // counting the number of nomal food
        long initialNormalCount = board.getFoods().stream()
                .filter(f -> f.gettype().equals("Normal"))
                .count();

        // the next is  to assume that there is already one normal food and to make sure of that
        assertEquals(1, initialNormalCount);

        // Calling Creatingfood() again and this should not affect the number of normal food as there is already one
        board.Creatingfood();

        // Count again
        long finalNormalCount = board.getFoods().stream()
                .filter(f -> f.gettype().equals("Normal"))
                .count();

        // They should be equal (still only 1)
        assertEquals(initialNormalCount, finalNormalCount);
    }



    // next is testing the Game class ( main function that will affect on the game state )|

    @Test
    public void testWallCollisionTriggersGameOver() {
Board board = new Board(300 , 200 );
snake snk  = board.getSnk();
        JLabel randomboard = new JLabel();

        Game game = new Game(board, snk, randomboard);
   board.setgame(game);

        game.setGameRunning(true);
        game.setGameOver(false);
   int num = (300/20) - ((300/20)/2);
         for (int i =0 ;i<num;i++){
     snk.move(Direction.RIGHT);
     if (i==num-1){
         game.wallcollision();
     }
     }
assertTrue(game.isGameOver());
    }

    @Test
    public void testSelfCollisionTriggersGameOver() {
        Board board = new Board(200, 200);
        snake snk = board.getSnk();
        JLabel randomboard = new JLabel();
        Game game = new Game(board, snk, randomboard);

        board.setgame(game);
        game.setGameRunning(true);
        game.setGameOver(false);

        // let's first add some segments to the snake body  and they have to be 5 segments  at least for sure
        snk.getBody().clear();
        snk.getBody().add(new point(5, 5)); // this will be the head of the snake
        snk.getBody().add(new point(4, 5));
        snk.getBody().add(new point(3, 5));
        snk.getBody().add(new point(2, 5));
        snk.getBody().add(new point(1, 5));

          // then let's move our snake towards one his body segments up and left and down
        snk.move(Direction.UP);
        snk.move(Direction.LEFT);
        snk.move(Direction.DOWN);

        game.selfcollision();

        assertTrue(game.isGameOver());
    }

    @Test
    public void testFoodCollisionEatsFoodAndUpdatesScore() {
        Board board = new Board(300 , 200 );
        snake snk  = board.getSnk();
        JLabel randomboard = new JLabel();
        Game game = new Game(board, snk, randomboard);        board.setgame(game);
        player pl = board.getPerson();
        int oldscore = pl.getScore();
        int oldnumberoffoods = board.getFoods().size();
        Food food = board.getFoods().getFirst();
        point pos = food.getPosition();
         int xvalue = pos.getX();
         int yvalue = pos.getY();
       point headpos =  snk.getBody().getFirst();
       int headx = headpos.getX();
       int heady = headpos.getY();
       int movesx;
       int movesy;
        Direction dx = (xvalue > headx) ? Direction.RIGHT : Direction.LEFT;
        Direction dy = (yvalue > heady) ? Direction.DOWN : Direction.UP;
        movesx = Math.abs(xvalue - headx);
        movesy = Math.abs(yvalue - heady);
            for (int i = 0; i < movesx; i++) {
        snk.move(dx);
            }
         for (int i = 0; i < movesy; i++) {
             snk.move(dy);
         }
        game.foodcollision();

        assertEquals( oldscore+ 1, pl.getScore());
        assertEquals( oldnumberoffoods, board.getFoods().size());
        assertNotEquals(board.getFoods().getFirst().getPosition(),snk.gethead());

       }
    @Test
    public void testEatingPoisonFoodUpdatesScoreCorrectly() {
        Board board = new Board(300 , 200 );
        snake snk = new snake(100, 100, 10);
        player p = board.getPerson();
        p.setScore(8);

        Food poison = new Poisonfood(Color.RED, -4);
        board.eaatingfood(poison);

        assertEquals(4, p.getScore());
    }

}


