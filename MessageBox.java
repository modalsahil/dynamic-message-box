import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Font;

/**   MessageBox class that adds a message box to the game to where when the user hits the "I"
 *    key, a messagebox is appears with text about how to play the game. This class draws the method
 *    select's the messagebox's x and y, and writes text in the messagebox to show to the user. 
 *    this class must be used in the driver to create a keybind for when the user hits the "I" key. 
 *    
 *    @AUTHOR: SAHIL SAHOTA :)
 * 
 */
public class MessageBox extends DragonGameShape {
    private int FRAME_WIDTH;
    private int FRAME_HEIGHT;

    private Dragon dragon; 

    private int x; //x position of the messagebox
    private int y;  //y position of the messagebox 

    private int insertedWidth; 
    private int numWords; //number of words in the messgebo
    private int timeToRemove; //how long should the messagebox stay up until removed 
    private Timer timerCountDown = null; //timer object 

    private String message; //text to be put into messagebox 

    private boolean isDisplayed = false; //boolean to check if the message box is displaying. 
    /**    Constructor for MessageBox, takes in the size of the screen, a dragon object and a string to
     *     put into the messagebox. 
     *     
     *     @PRE-CONDITION: width and height of the screen used to set the size of the messagebox.
     *     Dragon object to use the left and right boolean to check which way the dragon is facing
     *     String to take in text to put in the text box. 
     *     
     * 
     */
    public MessageBox(int frame_width, int frame_height, Dragon d, String message){
        this.FRAME_WIDTH = frame_width;
        this.FRAME_HEIGHT = frame_height;
        this.dragon = d;
        this.x = 0;
        this.y = 0;

        //initliazes variables to get the right time for the messagebox to disapear after. 
        this.message = message;
        numWords = this.message.split(" ").length;
        this.timeToRemove = (int) (numWords * 1000)/4; //the number of seconds the to keep the messagebox up for. 
    }

    /**     draws the messagebox to be displayed in the game to show instructions to the user 
     *      on how to play the game. 
     * 
     *      PRE-CONDITION: Graphics library
     *      
     *      @POST-CONDITION: Draws the message box 
     * 
     */
    public void draw(Graphics g2) {
        Graphics2D g = (Graphics2D) g2; //change Graphics to Graphics2D 
        
        
        //width and height needs to be smaller than the screen itself.
        int boxWidth = (int) FRAME_WIDTH/3;
        
        double boxHeightTemp = FRAME_HEIGHT*0.8-50; //temp variable to 
        int boxHeight = (int) boxHeightTemp;

        //draws the yellow border
        Color color;
        g.setColor(Color.YELLOW);
        g.fillRect(x-5, y-5, boxWidth+10, boxHeight+10);
        
        //draws the black background of the message box 
        g.setColor(Color.BLACK);
        g.fillRect(x, y, boxWidth, boxHeight);

        drawText(g); //calls the drawtext() method to draw the text on top of the text box. 
    }

    /**     Method to draw the text that goes inside of the text bot. Text is also wrapped 
     *      to fit the textbox so that their is no clipping out of the textbox. 
     *      
     *      @PRE-CONDITION: Requires the graphics package. 
     *      
     *      @POST-CONDITION: texr is drawn on the text box.
     * 
     */
    public void drawText (Graphics g2) {
        Graphics2D g = (Graphics2D) g2;

        Font myFont = new Font("Serrif", Font.BOLD, 20); //create a new font to be used in the messagebox
        g.setColor(Color.WHITE); //make the color of the font white. 
        g.setFont(myFont); //set the font to be used to be the one created aboce. 

        int maxX = FRAME_WIDTH/3; //how long each line of the method should be in pixels. 

        String[] words = message.split(" "); //splits each word of the message into an arr of Strings 
        int wordWidth; //to store the width of each word in the message as pixels  
        int lineTotal = 0; //to store the total length of each line 
        String newString = ""; //new string that includes line breaks 
        int space = g.getFontMetrics().stringWidth(" "); //used to add the length of space to the lineTotal 
        for (int i = 0; i < words.length; i++) {
            //for every word in the array...
            wordWidth = g.getFontMetrics().stringWidth(words[i]); //...find the width of each word (in pixel)...
            lineTotal += wordWidth + space; //... add it to the current lineTotal, also add a space because every word has a space after it.
            if (lineTotal >= maxX) {
                //when the lineTotal reaches the max length that it can be, add a line break "\n" to the newString 
                newString += "\n"; //add line break to newString
                lineTotal = wordWidth + space; //reset the lineTotal to be only the length of the word that didn't fit on the word plus a space. 
            }
            newString += words[i] + " "; //add each word to the newString. 

        }

    
        int lineHeight = g.getFontMetrics().getHeight(); //int that represents the  height of each line. 
        for (String line : newString.split("\n")) { //creates new array of String that is split up after every line break 
            //draw each line one by one so that line breaks can be added. 
            g.drawString(line, x+5, y += lineHeight);
        }


    }
    
    /**     Method to change the messagebox's x and y  to either the left or the right side of\
            the screen depending on if the dragon is  facing left or right. 
     *      
     *      @PRE: none.
     *      
     *      @POST: the message box's x and y are changed. 
     * 
     */
    public void move() {
        //System.out.println("got inside of move()");
        //if the dragon is facing right, move the message box to the left. 
        if (dragon.isFacingRight()) {
            this.x = (int) (FRAME_HEIGHT/8); //x position for when the dragon is facing right. 
            this.y = (int) (FRAME_WIDTH/3)-325; //y position for when dragon is facing right. 
        }

        //if the dragon is facing left, move the message box to right. 
        if (dragon.isFacingLeft()) {
            this.x = (int) (FRAME_HEIGHT/(2)+250); //x position for when the dragon is 
            this.y = (int) (FRAME_WIDTH/3)-325;
        }
    }

    /**     method to remove the messagebox by creating a timer and 
     *      
     *      @PRE-CONDITION: none.
     *      
     *      @POST-CONDITION: messagebox is removed. 
     * 
     */
    public void remove() {
        if(this.isDisplayed == true) { //if the messagebox is displayed, then it needs to bbe not drawn. 
            timerCountDown = new Timer(this.timeToRemove, new ActionListener() { //Timer for when isDisplay needs to be turned off. 
                    public void actionPerformed(ActionEvent e) {
                        isDisplayed = false; 
                    }
                });

            timerCountDown.setRepeats(false); 
            timerCountDown.start(); //start the timer. 
            if (this.isDisplayed == false) { 
                //once the timer has gone off, turn it off when isDisplayed turns false; 
                timerCountDown.stop();
            }
        }
    }

    /**    returns isDisplayed, this is to check if the messagebox is currently
     *     being displayed so
     * 
     *      @PRE-CONDITION: none.
     *      
     *      @POST-CONDITION: returns a boolean that 
     *      represents if the messagebox is displayed.
     */
    public boolean getIsDisplayed() {
        return this.isDisplayed; 
    }

    /**     a setter to change the value of isDisplayed.
     * 
     *      @PRE-CONDITION: Requires a boolean to change 
     *      the value of the isDisplayed var.
     *      
     *      @POST-CONDITION: value of isDisplayed is 
     *      changed. 
     *   
     * 
     */
    public void setIsDisplayed(boolean d) {
        this.isDisplayed = d;
    }
}
