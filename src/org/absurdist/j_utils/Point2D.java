package org.absurdist.j_utils;

/**
 *
 * @author sam
 */

// <editor-fold defaultstate="collapsed" desc="Point2D class">

/*
 * A Point2D holds two integer coordinates 
 */

public class Point2D 
{
    
    // <editor-fold defaultstate="collapsed" desc="constructors">
    
    public Point2D(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    // </editor-fold>
      
    // <editor-fold defaultstate="collapsed" desc="public methods">
    
    public void offset(int dx, int dy)
    {
        x += dx;
        y += dy;
    }
    
    public void set (int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="overridden Object methods">
    
    @Override
    public boolean equals(Object o)
    {
        if ( !(o instanceof Point2D) )
            return false;
        
        Point2D test = (Point2D) o;
        
        return ( (x == test.x) && (y == test.y) );
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="instance variables">
    
    public int x;
    public int y;
    
    // </editor-fold>
}

// </editor-fold>
