import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class StoryTeller
{
    public File file = new File("src/story.txt"); //定义文件

    public boolean isActive;
    public int locX, locY;
    public int pages = 0, pageNum = 0;
    public int rowLen = 28, rowLim = 4; //行长度（字符个数），每页行数

    public Font myFont = new Font("微软雅黑", Font.PLAIN, 24);
    public String[] strNames, strContents;

    //当前一页相关参数
    public int rowNum = 0;
    public String[] rowContents;

    public boolean isTyping = false;
    public int typeDelay = 0;
    public int rowIndex = 0, wordIndex = 0;
    public String typedString = "";

    //public Image imgLangCl, app; //对话框相关资源

    public StoryTeller()
    {
        Reset();
    }

    public void Reset()
    {
        //imgLangCl = Toolkit.getDefaultToolkit().getImage(
                //StoryTeller.class.getResource("langColumn.png"));
        //app = imgLangCl;  //绘制说话人物头像
        isActive = false;
        locX = 260; locY = 710;
        pages = 0; pageNum = 0;
        rowLen = 28; rowLim = 4;

        isTyping = true;
        typeDelay = 20;
        rowIndex = 0; wordIndex = 0;
        typedString = "";

        countTxtLines(); //修改了pages
        readTxt();
        ChangePage();
    }

    public void ChangePage()
    {
        try
        {
            String pageContent = strContents[pages-pageNum]; //一页可显示的内容 pages-pageNum
            rowNum = (pageContent.length() + rowLen - 1)/rowLen; //当前页的行数
            rowContents = new String[rowNum]; //每行内容

            for(int i=0; i<rowNum; i++) //取出各行原始内容
            {
                if(i == rowNum-1) rowContents[i] = pageContent.substring(rowLen*i);
                else rowContents[i] = pageContent.substring(rowLen*i, rowLen*(i+1));
            }

            for(int i=0; i<rowNum; i++)  //各行以标点符号打头的修正
            {
                String headWord = rowContents[i].substring(0,1);
                if(i > 0)
                {
                    int mov = 0; //考虑引号和省略号，有时需移动不止一个标点
                    while(headWord.matches("，") || headWord.matches("。")
                            || headWord.matches("”") || headWord.matches("’")
                            || headWord.matches("？") || headWord.matches("！")
                            || headWord.matches("…"))
                    {
                        mov++;
                        rowContents[i-1] = pageContent.substring(rowLen*(i-1), rowLen*i+mov); //上一行加上本行首的标点

                        if(i == rowNum-1) //本行去掉首标点即可
                        {
                            if (rowContents[i].length() == 1) rowContents[i] = "";
                            else rowContents[i] = rowContents[i].substring(1);
                        }
                        else //本行不是最后一行的情况
                        {
                            for(int j=i; j<rowNum; j++)
                            {
                                if(j == rowNum-1) //最后行去首
                                {
                                    if (rowContents[j].length() == 1) rowContents[j] = "";
                                    else rowContents[j] = rowContents[j].substring(1);
                                }
                                else //其余行需去首加尾
                                {rowContents[j] = pageContent.substring(rowLen*j+mov, rowLen*(j+1)+mov);}
                            }
                        }
                        headWord = rowContents[i].substring(0,1);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }

    public void PaintLangColumn(Graphics2D g2, Game gi)
    {
        g2.setFont(myFont);
        g2.setColor(Color.black);
        //gp.drawImage(imgLangCl, locX, locY, gi);
        //gp.drawString(strNames[pages-pageNum], 300, 760); //写说话人名字

        if(isTyping)
        {
            for(int i=0; i<rowIndex; i++) g2.drawString(rowContents[i], 324-75, 810+30*i);
            g2.drawString(typedString,324-75,810+30*rowIndex);
            typeDelay--;
            if(typeDelay <= 0) //过了延时，进行打字动作
            {
                typeDelay = 20; //重置延时 25
                if(rowIndex >= rowNum) ResetTyper();
                else
                {
                    if(wordIndex < rowContents[rowIndex].length())
                    { typedString = rowContents[rowIndex].substring(0, wordIndex+1); wordIndex++; }
                    else
                    { typedString = ""; wordIndex = 0; rowIndex++; }
                }
            }
        }
        else for(int i=0; i<rowNum; i++) g2.drawString(rowContents[i], 324-75, 810+30*i);
    }

    public void ResetTyper()
    {
        isTyping = !isTyping; typedString = ""; wordIndex = 0; rowIndex = 0;
    }

    public void readTxt()  //读txt文件
    {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),"UTF-8"))) //读缓存
        {
            //打开文件，使用阅读器
            String tempString;
            int count = 0;
            while ((tempString = reader.readLine()) != null) // 一次读入一行，直到读入null为文件结束N
            {
                String[] tempSentence = tempString.split("]");

                //在此处判定该行需不需要切分
                int rowN = (tempSentence[1].length() + rowLen - 1) / rowLen;
                int pageN = (rowN + rowLim - 1) / rowLim;
                for (int i = 0; i < pageN; i++)
                {
                    int head, tail;
                    if (i < pageN - 1) { head = i * rowLim * rowLen; tail = (i + 1) * rowLim * rowLen; }
                    else { head = i * rowLim * rowLen; tail = tempSentence[1].length(); }

                    String tempHead = tempSentence[0].substring(1);
                    String tempPiece = tempSentence[1].substring(head, tail);
                    if (tempHead.equals("*")) strNames[count] = " ";
                    else strNames[count] = "【" + tempHead + "】";
                    try { strContents[count] = tempPiece; }
                    catch (Exception e) { System.out.print(e); }
                    count++;
                }
            }
        }
        catch (IOException e) //对输入输出的容错处理
        {
            e.printStackTrace();
        }
    }

    public void countTxtLines()
    {
        try(BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file),"UTF-8"))) //读缓存
        {
            //打开文件，使用阅读器
            String tempString;
            while ((tempString = reader.readLine()) != null) // 一次读入一行，直到读入null为文件结束
            {
                tempString = new String (tempString.getBytes(StandardCharsets.UTF_8));

                String[] tempSentence = tempString.split("]");
                int rowN = 0;
                try {rowN = (tempSentence[1].length() + rowLen - 1) / rowLen;}
                catch(Exception e) {System.out.print(e);}
                int pageN = (rowN + rowLim - 1) / rowLim;
                pages += pageN;
            }

            strNames = new String[pages];
            strContents = new String[pages];
            pageNum = pages;
        }
        catch (IOException e) //对输入输出的容错处理
        {
            e.printStackTrace();
        }
    }
}
