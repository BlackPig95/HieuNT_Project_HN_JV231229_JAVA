package ra.business.config;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectAppendStream extends ObjectOutputStream
{

    public ObjectAppendStream(OutputStream out) throws IOException
    {
        super(out);
    }

    protected ObjectAppendStream() throws IOException, SecurityException
    {
    }

    @Override
    protected void writeStreamHeader() throws IOException
    {
//        super.writeStreamHeader();
    }
}
