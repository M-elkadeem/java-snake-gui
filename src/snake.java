import java.util.LinkedList;

public class snake {
   private LinkedList <point> body;


    public snake() {}

    public snake(int width, int height, int tilesize) {
            body= new LinkedList<>() ;
        body.add(new point((width/tilesize)/2,(height/tilesize)/2));// the head coordinates
 }
 public point gethead(){
        return body.getFirst();
 }
 public LinkedList<point>getBody(){
        return body ;
 }
 public int getlength (){
        return body.size();// this will return the length of the snake
 }
 public void move(Direction heading){
// in that function , we will make all the modification on the snake whether the moving or the action themselves
     point head = body.getFirst();// this head contains the x and y coordinates
     int headX = head.getX();
     int headY = head.getY();

     switch (heading){
         case UP -> headY--;
         case DOWN -> headY++;
         case LEFT -> headX--;
         case RIGHT -> headX++;
     }
     point newHead = new point(headX, headY);// this will shift the head to a new place
     body.addFirst(newHead);// here we have to add the new head to the body
     body.removeLast();// the old position of the head is still there so we have to remove it

 }
 public void grow(Food food){
     point tail = body.getLast();

     int segmentsToAdd = 0;
     if (food.gettype().equals("Normal")){
         segmentsToAdd = food.getPointvalue();
     }else if(food.gettype().equals("Golden")){
         segmentsToAdd = food.getPointvalue();
     }
     for (int i = 0; i < segmentsToAdd; i++) {
         body.addLast(new point(tail.getX(), tail.getY()));
     }
 }

 public void shrinking(Food food){
 int newsize = body.size() + food.getPointvalue();//  this we will determine how much we want cut from the snake
   if(newsize < 1 ){
       newsize = 1;
   }
     body.subList(newsize, body.size()).clear();
 }
}


