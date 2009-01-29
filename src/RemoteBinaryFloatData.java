import java.util.Vector;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RemoteBinaryFloatData extends BinaryFloatData{

    private int valuesPerEntry;

    RemoteBinaryFloatData(String filename, SampleData sd, MarkerData md, int valuesPerEntry, String collection) {
        super(filename, sd, md, valuesPerEntry, collection);
        this.valuesPerEntry = valuesPerEntry;
    }

    public Vector<float[]> getRecord(String name) {
        try{
            BufferedInputStream bntIS = new BufferedInputStream(
                    new FileInputStream("/Users/jcbarret/"+collection+"."+name+".bnt"),8192);

            //read raw snp data
            byte[] rawSnpData = new byte[bytesPerRecord];
            bntIS.read(rawSnpData, 0, bytesPerRecord);


            ByteBuffer rawDataBuffer = ByteBuffer.wrap(rawSnpData);
            rawDataBuffer.order(ByteOrder.LITTLE_ENDIAN);

            Vector<float[]> record = new Vector<float[]>();
            for (int i = 0; i < numInds; i++){
                float[] send = new float[valuesPerEntry];
                for (int j = 0; j < valuesPerEntry; j++){
                    send[j] = rawDataBuffer.getFloat();
                }
                record.add(send);
            }

            return record;
        }catch (IOException e){
            System.out.println("e = " + e);
        }
        return (null);
    }
}
