/**
 * Created by ZHANGS on 7/7/2017.
 */
//import java.util.Arrays;
import java.util.ArrayList;

public class editdistancebydynamicprogramming {

    int editdistance;
    String s1;
    String s2;
    int ctable[][];
    int n;
    int m;

    public static void main(String[] args){

       //computer comupter
        editdistancebydynamicprogramming o=new editdistancebydynamicprogramming(args[0], args[1]);
        o.edbydp();

        System.out.println(o.editdistance);
        o.findalignment(o.m-1, o.n-1);
    }

    ArrayList<Character> s1List = new ArrayList<Character>();
    ArrayList<Character> s2List = new ArrayList<Character>();

    public editdistancebydynamicprogramming(String s1, String s2)
    {
        this.s1=s1;
        this.s2=s2;
    }

    void	printalignment()
    {
        System.out.println("=====Optimal Alignment =======");
        for(int i=s1List.size()-1; i>=0;i--)
        {
            System.out.print(s1List.get(i));
            System.out.print("\t");
            System.out.println(s2List.get(i));
        }
    }


    void findalignment(int row, int col)
    {
        int newrow;
        int newcol;

        if (row==0&&col==0)
        {
            printalignment();
            return;
        }

        if (ctable[row][col]==ctable[row-1][col]+1) //up
        {

            s1List.add('-');
            s2List.add(s2.charAt(row-1));
            findalignment(row-1,col);
            s1List.remove(s1List.size()-1);
            s2List.remove(s2List.size()-1);
        }

        if (ctable[row][col]==ctable[row][col-1]+1) //up
        {
            s1List.add(s1.charAt(col-1));
            s2List.add('-');
            findalignment(row,col-1);
            s1List.remove(s1List.size()-1);
            s2List.remove(s2List.size()-1);
        }


        if (ctable[row][col]==ctable[row-1][col-1] && s1.charAt(col-1)==s2.charAt(row-1)) //up
        {
            s1List.add(s1.charAt(col-1));
            s2List.add(s2.charAt(row-1));
            findalignment(row-1,col-1);
            s1List.remove(s1List.size()-1);
            s2List.remove(s2List.size()-1);
        }

        else if (ctable[row][col]==ctable[row-1][col-1]+1 && s1.charAt(col-1)!=s2.charAt(row-1)) //diagnal
        {
            s1List.add(s1.charAt(col-1));
            s2List.add(s2.charAt(row-1));
            findalignment(row-1,col-1);
            s1List.remove(s1List.size()-1);
            s2List.remove(s2List.size()-1);
        }




    }


    void printtable()
    {


        System.out.println();
        System.out.println("=============");

        for(int i=0;i<m; i++)  // m for row, n for column
        {
            for(int j=0;j<n; j++)
            {
                System.out.print(ctable[i][j]+"\t");
            }

            System.out.println();
        }

    }

    void edbydp(){

        //step 1: dynamically create a table of size of |s1|+1 and |s2|+1
        n=s1.length()+1;
        m=s2.length()+1;

        ctable=new int [m][n]; //str 2 is vertical, str 1 is horizontal

        //step 2: initialize the top left corner
        ctable[0][0]=0;

        //step 3: initialize the first row and the first column
        for(int i=1; i<n; i++)
            ctable[0][i]=i;
        for(int j=1; j<m; j++)
            ctable[j][0]=j;

        printtable();

        //step 4:
        int subcost;
        for (int i=1; i<m;i++)   // pay attention to m for row, n for column
            for (int j=1; j<n; j++)
            {

                if (s2.charAt(i-1) == s1.charAt(j-1))
                    subcost = 0;
                else
                    subcost = 1;

                int minimal=ctable[i-1][j] + 1;   // initialize minimal to deletion

                if (minimal>ctable[i][j-1] + 1) // if insertion cost is smaller
                    minimal=ctable[i][j-1] + 1;
                if (minimal>ctable[i-1][j-1] + subcost)
                    minimal=ctable[i-1][j-1] + subcost; // substitution cost if mismatch or 0 if match )

                ctable[i][j]=minimal;
            }

        printtable();

        editdistance= ctable[m-1][n-1];

    }

}