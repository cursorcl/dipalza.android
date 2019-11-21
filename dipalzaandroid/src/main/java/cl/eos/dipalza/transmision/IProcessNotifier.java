package cl.eos.dipalza.transmision;

import android.os.Handler;


public interface IProcessNotifier
{
	void process(Object paramObject);

	void addToProcess(Object paramObject);
	
	void setHandler(Handler handler);
}
