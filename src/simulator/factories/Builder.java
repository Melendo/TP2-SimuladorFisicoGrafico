package simulator.factories;

import org.json.JSONObject;

public abstract class Builder<T> {
	protected String _typeTag;
	protected String _desc;

	//SOS MAGIC NUMBERS
	public Builder(String typeTag, String desc) {
		if (typeTag == null || desc == null || typeTag.length() == 0 || desc.length() == 0)
			throw new IllegalArgumentException("Invalid type/desc");
		
		_typeTag = typeTag;
		_desc = desc;
	}

	public String getTypeTag() {
		return _typeTag;
	}

	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		JSONObject data = new JSONObject();
		info.put("type", _typeTag);
		info.put("desc", _desc);
		getData(data);
		info.put("data", data);
		return info;
	}
	
	protected abstract void getData(JSONObject data);

	@Override
	public String toString() {
		return _desc;
	}

	protected abstract T createInstance(JSONObject data); 
}
