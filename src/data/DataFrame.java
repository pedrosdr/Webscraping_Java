package data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataFrame
{
    // fields
    private List<DataRow> data;

    // constructors
    public DataFrame() {
        this.data = new ArrayList<>();
    };

    // methods
    public void append(DataRow row)
    {
        data.add(row);
    }

    public void toCSV(String path)
    {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(path)))
        {
            writer.write("codigo;ano;etapa;disciplina;percentual;nivel\n");
            for(DataRow row : data)
                writer.write(String.format("%s;%s;%s;%s;%s;%s\n", row.getCodigo(), row.getAno(), row.getEtapa(), row.getDisciplina(), row.getPercentual(), row.getNivel()));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[[codigo   ano   etapa   disciplina   percentual   nivel]\n");
        for(int i = 0; i < data.size(); i++)
        {
            sb.append(data.get(i).toString());
            if(i != data.size()-1)
                sb.append("\n");
        }
        sb.append("]");
        return sb.toString();
    }
}
