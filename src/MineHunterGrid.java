/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author VirusFreeNewt
 */
public class MineHunterGrid extends JPanel implements ActionListener, MineHunterInterface
{
    private JButton[][] tiles;
    private JButton clicked;
    private int clickedX, clickedY, percentMines, mineCount, numMines;
    private int delay = 1000; //in ms
    private int gridDimensions;
    private MineHunterControls controls;
    private Color buttonColor = null;
    private int[][] tileNum; // the number of mines surrounding each tile
    private boolean[][] mines;
    private boolean[][] locatedMines;
    private ImageIcon flag = new ImageIcon("images/mineSweeperFlag.jpg");
    
    private Color[] buttonColors = {Color.WHITE, Color.GRAY, Color.GREEN, Color.YELLOW, Color.PINK, Color.BLUE, Color.RED, Color.BLACK};

    public MineHunterGrid(int gridDimensions, int percentMines)
    {
        numMines = (int)(Math.pow(gridDimensions, 2) * ((double)percentMines / 100));

        this.gridDimensions = gridDimensions;

        this.percentMines = percentMines;

        tiles = new JButton[gridDimensions][gridDimensions];

        mines = new boolean[gridDimensions][gridDimensions];

        locatedMines = new boolean[gridDimensions][gridDimensions];

        tileNum = new int[gridDimensions][gridDimensions];

        makeMines(percentMines);

        getTileNum();

        //Sets the layout of the JPanel and builds the grid of buttons
        //Each button is assigned an ActionListener
        //Each button is assigned a rightClickListener
        setLayout(new GridLayout(gridDimensions, gridDimensions));
    	setPreferredSize(new Dimension(800, 800));
        
        for (int i = 0; i < tiles.length; ++i)
        {
            for (int j = 0; j < tiles[i].length; ++j)
            {
         	tiles[i][j] = new JButton();       
         	tiles[i][j].addActionListener(this);
            tiles[i][j].addMouseListener(new rightClickListener());
         	add(tiles[i][j]);
            }
        }
    }

    private void getTileNum()
    {
        for (int i = 0; i < tiles.length; ++i)
        {
            for(int j = 0; j < tiles[i].length; ++j)
            {
                if(mines[i][j])
                {
                    tileNum[i][j] = -1;
                    continue;
                }
                tileNum[i][j] = getSurroundingMines(i, j);
            }
        }
    }

   /**
    * Sets the mines on the grid.  Wherever a mind occurs on the grid
    * the mines array will be set to true
    * @param percent mines on the grid
    */
    public void makeMines(int percent)
    {
        mineCount = 0;
        int randX;
        int randY;
        while(mineCount < numMines)
        {
            randX = (int)(Math.random() * gridDimensions);
            randY = (int)(Math.random() * gridDimensions);
            if(!mines[randX][randY])
            {
                mines[randX][randY] = true;
                ++mineCount;
            }
        }
    }
    
   /**
    * Displays the mines on the grid.  If there is a mine
    * the background of the button will be red
    * the flag icon will appear. 
    * Use .setBackground(Color.RED) to set the color
    * Use .setIcon(flag) to show 
    * the flag
    */
    public void showMines()
    {
        for(int i = 0; i < mines.length; ++i)
        {
            for(int j = 0; j < mines[i].length; ++j)
            {
                if(mines[i][j])
                {
                    tiles[i][j].setBackground(Color.RED);
                }
            }
        }
    }
    
   /**
    * Checks the mines array to see if a mine is set at the 
    * x and y location of the clicked button.  I a mine is 
    * set showMines() will be called and all the buttons will appear
    * to indicate that the player has lost
    * @param row the row of the button clicked
    * @param col the column of the button clicked
    */
    public void checkForMine(int row, int col)
    {
        if(mines[row][col])
        {
            showMines();
        }
    }

    private int getSurroundingMines(int row, int col)
    {
        mineCount = 0;
        int leftOffset = 1;
        int rightOffset = 1;
        int topOffset = 1;
        int bottomOffset = 1;

        if(row == 0)
        {
            leftOffset = 0;
        }
        else if(row == gridDimensions - 1)
        {
            rightOffset = 0;
        }

        if(col == 0)
        {
            topOffset = 0;
        }
        else if(col == gridDimensions - 1)
        {
            bottomOffset = 0;
        }

        for(int i = row - leftOffset; i < row + rightOffset; ++i)
        {
            for(int j = col - topOffset; j < col + bottomOffset; ++j)
            {
                if(mines[i][j])
                {
                    ++mineCount;
                }
            }
        }
        return mineCount;
    }
     /**
     * Counts the number of mines surrounding the clicked button
     * Depending on the number of mines located, the button clicked will be set to
     * a different color in the buttonColors array
     * @param row the row of the button clicked
     * @param col the column of the button clicked
     */
    public void paintTiles(int row, int col)
    {
        int tileNum = getSurroundingMines(row, col);
        if(tileNum != -1)
        {
            tiles[row][col].setBackground(buttonColors[tileNum]);
        }
    }

    /**
     * Checks if all the mines have been located.  
     * If all the mines have been located in the mines array, 
     * returns true, otherwise, returns false
    */
    public boolean isDone()
    {
        mineCount = 0;
        for(int i = 0; i < mines.length; ++i)
        {
            for(int j = 0; j < mines[i].length; ++j)
            {
                if(locatedMines[i][j])
                {
                    ++mineCount;
                }
            }
        }
        return mineCount == numMines;
    }

    /**
     * DO NOT EDIT
     * Resets the game.  
     */
    public void resetGame()
    {
        for(int row = 0; row < mines.length; ++row)
        {
            for(int col = 0; col < mines[row].length; ++col)
            {
                mines[row][col] = false;
                locatedMines[row][col] = false;
                tiles[row][col].setBackground(null);
                tiles[row][col].setIcon(null);
            }
        }
        makeMines(percentMines);
    }
    
    /**
     * DO NOT EDIT
     * resets the game if the reset button is clicked
     * @param i indicates whether to reset game
     */
    public void doButtonAction(int i)
    {
        if(i == 0)
        {
            resetGame();
        }
    }
    
    /**
     * DO NOT EDIT
     * Sets the rightClicked Button with a flag
     * If a mine is found by the player
     * @param mineX
     * @param mineY 
     */
    public void identifyMines(int mineX, int mineY)
    {
        if(!locatedMines[mineX][mineY])
        {
            tiles[mineX][mineY].setIcon(flag);
            locatedMines[mineX][mineY] = true;
        }
        else
        {
            tiles[mineX][mineY].setIcon(null);
            locatedMines[mineX][mineY] = false;
        }
    }
    
    /**
     * DO NOT EDIT
     * Locates the button that is clicked on the grid
     * locates the x and y location of the button clicked
     * @param clicked the button clicked
     */
    public void locateClicked(JButton clicked)
    {
        for(int rows = 0; rows < tiles.length; ++rows)
        {
            for(int cols = 0; cols < tiles[rows].length; ++cols)
            {
                if(clicked == tiles[rows][cols])
                {
                this.clicked = clicked;
                clickedX = rows;
                clickedY = cols;
                }
            }
        }
    }
    
    /**
     * DO NOT EDIT
     * Sets the location of the button in the identifyMines array 
     * to true.  
     * Checks if all the mines have been identified
     * Sets the icon of the right clicked button to a flag
     */ 
    private class rightClickListener  extends MouseAdapter
    {
        public void mouseClicked (MouseEvent e) 
        {       			// write here your event handling code
            if(e.getModifiersEx() == MouseEvent.META_DOWN_MASK)
            {
                System.out.println("HEY I RIGHT CLICKED");
                locateClicked((JButton) e.getSource());
                identifyMines(clickedX, clickedY);
                if(isDone())
                {
                   System.out.println("YOU WIN!"); 
                }
            }
        }
    } 
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        clicked = (JButton) e.getSource();
        locateClicked(clicked);
        paintTiles(clickedX, clickedY);
        checkForMine(clickedX, clickedY);
    }
}
