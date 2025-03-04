package talys.fieldmappingslineage_0_1;
import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;
@SuppressWarnings("unused")
public class FieldMappingsLineage implements TalendJob {
protected static void logIgnoredError(String message, Throwable cause) {
System.err.println(message);
if (cause != null) {
cause.printStackTrace();
}
}
public final Object obj = new Object();
private Object valueObject = null;
public Object getValueObject() {
return this.valueObject;
}
public void setValueObject(Object valueObject) {
this.valueObject = valueObject;
}
private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();
private final static String utf8Charset = "UTF-8";
public class PropertiesWithType extends java.util.Properties {
private static final long serialVersionUID = 1L;
private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();
public PropertiesWithType(java.util.Properties properties) {
super(properties);
}
public PropertiesWithType() {
super();
}
public void setContextType(String key, String type) {
propertyTypes.put(key, type);
}
public String getContextType(String key) {
return propertyTypes.get(key);
}
}
private java.util.Properties defaultProps = new java.util.Properties();
public class ContextProperties extends PropertiesWithType {
private static final long serialVersionUID = 1L;
public ContextProperties(java.util.Properties properties) {
super(properties);
}
public ContextProperties() {
super();
}
public void synchronizeContext() {
}
public String getStringValue(String key) {
String origin_value = this.getProperty(key);
if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
return null;
}
return origin_value;
}
}
protected ContextProperties context = new ContextProperties();
public ContextProperties getContext() {
return this.context;
}
private final String jobVersion = "0.1";
private final String jobName = "FieldMappingsLineage";
private final String projectName = "TALYS";
public Integer errorCode = null;
private String currentComponent = "";
private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
private RunStat runStat = new RunStat();
private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";
private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";
public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
talendDataSources.put(dataSourceEntry.getKey(),
new routines.system.TalendDataSource(dataSourceEntry.getValue()));
}
globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
}
public void setDataSourceReferences(List serviceReferences) throws Exception {
java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();
for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
dataSources.put(entry.getKey(), entry.getValue());
talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
}
globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
}
private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));
public String getExceptionStackTrace() {
if ("failure".equals(this.getStatus())) {
errorMessagePS.flush();
return baos.toString();
}
return null;
}
private Exception exception;
public Exception getException() {
if ("failure".equals(this.getStatus())) {
return this.exception;
}
return null;
}
private class TalendException extends Exception {
private static final long serialVersionUID = 1L;
private java.util.Map<String, Object> globalMap = null;
private Exception e = null;
private String currentComponent = null;
private String virtualComponentName = null;
public void setVirtualComponentName(String virtualComponentName) {
this.virtualComponentName = virtualComponentName;
}
private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
this.currentComponent = errorComponent;
this.globalMap = globalMap;
this.e = e;
}
public Exception getException() {
return this.e;
}
public String getCurrentComponent() {
return this.currentComponent;
}
public String getExceptionCauseMessage(Exception e) {
Throwable cause = e;
String message = null;
int i = 10;
while (null != cause && 0 < i--) {
message = cause.getMessage();
if (null == message) {
cause = cause.getCause();
} else {
break;
}
}
if (null == message) {
message = e.getClass().getName();
}
return message;
}
@Override
public void printStackTrace() {
if (!(e instanceof TalendException || e instanceof TDieException)) {
if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
}
globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
}
if (!(e instanceof TDieException)) {
if (e instanceof TalendException) {
e.printStackTrace();
} else {
e.printStackTrace();
e.printStackTrace(errorMessagePS);
FieldMappingsLineage.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(FieldMappingsLineage.this, new Object[] { e, currentComponent, globalMap });
break;
}
}
if (!(e instanceof TDieException)) {
}
} catch (Exception e) {
this.e.printStackTrace();
}
}
}
}
public void tDBInput_3_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tAdvancedHash_row3_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tAdvancedHash_row2_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tDBInput_3_onSubJobError(exception, errorComponent, globalMap);
}
public void tDBInput_3_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class OutCSVStruct implements routines.system.IPersistableRow<OutCSVStruct> {
final static byte[] commonByteArrayLock_TALYS_FieldMappingsLineage = new byte[0];
static byte[] commonByteArray_TALYS_FieldMappingsLineage = new byte[0];
public String name;
public String getName() {
return this.name;
}
public String expression;
public String getExpression() {
return this.expression;
}
public String name_1;
public String getName_1() {
return this.name_1;
}
public String type;
public String getType() {
return this.type;
}
public String description;
public String getDescription() {
return this.description;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_FieldMappingsLineage.length) {
if (length < 1024 && commonByteArray_TALYS_FieldMappingsLineage.length == 0) {
commonByteArray_TALYS_FieldMappingsLineage = new byte[1024];
} else {
commonByteArray_TALYS_FieldMappingsLineage = new byte[2 * length];
}
}
dis.readFully(commonByteArray_TALYS_FieldMappingsLineage, 0, length);
strReturn = new String(commonByteArray_TALYS_FieldMappingsLineage, 0, length, utf8Charset);
}
return strReturn;
}
private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_TALYS_FieldMappingsLineage.length) {
if (length < 1024 && commonByteArray_TALYS_FieldMappingsLineage.length == 0) {
commonByteArray_TALYS_FieldMappingsLineage = new byte[1024];
} else {
commonByteArray_TALYS_FieldMappingsLineage = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_TALYS_FieldMappingsLineage, 0, length);
strReturn = new String(commonByteArray_TALYS_FieldMappingsLineage, 0, length, utf8Charset);
}
return strReturn;
}
private void writeString(String str, ObjectOutputStream dos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.name = readString(dis);
this.expression = readString(dis);
this.name_1 = readString(dis);
this.type = readString(dis);
this.description = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.name = readString(dis);
this.expression = readString(dis);
this.name_1 = readString(dis);
this.type = readString(dis);
this.description = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.name, dos);
writeString(this.expression, dos);
writeString(this.name_1, dos);
writeString(this.type, dos);
writeString(this.description, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.name, dos);
writeString(this.expression, dos);
writeString(this.name_1, dos);
writeString(this.type, dos);
writeString(this.description, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("name=" + name);
sb.append(",expression=" + expression);
sb.append(",name_1=" + name_1);
sb.append(",type=" + type);
sb.append(",description=" + description);
sb.append("]");
return sb.toString();
}
public int compareTo(OutCSVStruct other) {
int returnValue = -1;
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
final static byte[] commonByteArrayLock_TALYS_FieldMappingsLineage = new byte[0];
static byte[] commonByteArray_TALYS_FieldMappingsLineage = new byte[0];
public long field_mapping_id;
public long getField_mapping_id() {
return this.field_mapping_id;
}
public long source_fields_id;
public long getSource_fields_id() {
return this.source_fields_id;
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.field_mapping_id = dis.readLong();
this.source_fields_id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.field_mapping_id = dis.readLong();
this.source_fields_id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.field_mapping_id);
dos.writeLong(this.source_fields_id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.field_mapping_id);
dos.writeLong(this.source_fields_id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("field_mapping_id=" + String.valueOf(field_mapping_id));
sb.append(",source_fields_id=" + String.valueOf(source_fields_id));
sb.append("]");
return sb.toString();
}
public int compareTo(row1Struct other) {
int returnValue = -1;
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public static class after_tDBInput_3Struct implements routines.system.IPersistableRow<after_tDBInput_3Struct> {
final static byte[] commonByteArrayLock_TALYS_FieldMappingsLineage = new byte[0];
static byte[] commonByteArray_TALYS_FieldMappingsLineage = new byte[0];
public long field_mapping_id;
public long getField_mapping_id() {
return this.field_mapping_id;
}
public long source_fields_id;
public long getSource_fields_id() {
return this.source_fields_id;
}
public void readData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.field_mapping_id = dis.readLong();
this.source_fields_id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.field_mapping_id = dis.readLong();
this.source_fields_id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
dos.writeLong(this.field_mapping_id);
dos.writeLong(this.source_fields_id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.field_mapping_id);
dos.writeLong(this.source_fields_id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("field_mapping_id=" + String.valueOf(field_mapping_id));
sb.append(",source_fields_id=" + String.valueOf(source_fields_id));
sb.append("]");
return sb.toString();
}
public int compareTo(after_tDBInput_3Struct other) {
int returnValue = -1;
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);
final boolean execStat = this.execStat;
String iterateId = "";
String currentComponent = "";
java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
try {
boolean resumeIt = true;
if (globalResumeTicket == false && resumeEntryMethodName != null) {
String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
resumeIt = resumeEntryMethodName.equals(currentMethodName);
}
if (resumeIt || globalResumeTicket) {
globalResumeTicket = true;
tDBInput_1Process(globalMap);
tDBInput_2Process(globalMap);
row1Struct row1 = new row1Struct();
OutCSVStruct OutCSV = new OutCSVStruct();
ok_Hash.put("tFileOutputDelimited_1", false);
start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
currentComponent = "tFileOutputDelimited_1";
if (execStat) {
runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "OutCSV");
}
int tos_count_tFileOutputDelimited_1 = 0;
String fileName_tFileOutputDelimited_1 = "";
fileName_tFileOutputDelimited_1 = (new java.io.File("C:/Users/pc/Desktop/out2.csv")).getAbsolutePath()
.replace("\\", "/");
String fullName_tFileOutputDelimited_1 = null;
String extension_tFileOutputDelimited_1 = null;
String directory_tFileOutputDelimited_1 = null;
if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1
.lastIndexOf("/")) {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
extension_tFileOutputDelimited_1 = "";
} else {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("."));
extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
}
directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("/"));
} else {
if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
fileName_tFileOutputDelimited_1.lastIndexOf("."));
extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1
.substring(fileName_tFileOutputDelimited_1.lastIndexOf("."));
} else {
fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
extension_tFileOutputDelimited_1 = "";
}
directory_tFileOutputDelimited_1 = "";
}
boolean isFileGenerated_tFileOutputDelimited_1 = true;
java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
if (filetFileOutputDelimited_1.exists()) {
throw new RuntimeException("The particular file \"" + filetFileOutputDelimited_1.getAbsoluteFile()
+ "\" already exist. If you want to overwrite the file, please uncheck the"
+ " \"Throw an error if the file already exist\" option in Advanced settings.");
}
int nb_line_tFileOutputDelimited_1 = 0;
int splitedFileNo_tFileOutputDelimited_1 = 0;
int currentRow_tFileOutputDelimited_1 = 0;
final String OUT_DELIM_tFileOutputDelimited_1 =
";"
;
final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 =
"\n"
;
if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
if (!dir_tFileOutputDelimited_1.exists()) {
dir_tFileOutputDelimited_1.mkdirs();
}
}
java.io.Writer outtFileOutputDelimited_1 = null;
java.io.File fileToDelete_tFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);
if (fileToDelete_tFileOutputDelimited_1.exists()) {
fileToDelete_tFileOutputDelimited_1.delete();
}
outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "ISO-8859-15"));
resourceMap.put("out_tFileOutputDelimited_1", outtFileOutputDelimited_1);
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
ok_Hash.put("tMap_1", false);
start_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tMap_1";
if (execStat) {
runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
}
int tos_count_tMap_1 = 0;
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct>) globalMap
.get("tHash_Lookup_row2"));
row2Struct row2HashKey = new row2Struct();
row2Struct row2Default = new row2Struct();
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) globalMap
.get("tHash_Lookup_row3"));
row3Struct row3HashKey = new row3Struct();
row3Struct row3Default = new row3Struct();
class Var__tMap_1__Struct {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
OutCSVStruct OutCSV_tmp = new OutCSVStruct();
ok_Hash.put("tDBInput_3", false);
start_Hash.put("tDBInput_3", System.currentTimeMillis());
currentComponent = "tDBInput_3";
int tos_count_tDBInput_3 = 0;
int nb_line_tDBInput_3 = 0;
java.sql.Connection conn_tDBInput_3 = null;
String driverClass_tDBInput_3 = "org.postgresql.Driver";
java.lang.Class jdbcclazz_tDBInput_3 = java.lang.Class.forName(driverClass_tDBInput_3);
String dbUser_tDBInput_3 = "postgres";
final String decryptedPassword_tDBInput_3 = routines.system.PasswordEncryptUtil
.decryptPassword("enc:routine.encryption.key.v1:S2TX2I1+IikJ7kJnh3YnPhshTL0hAShxQmquSRciEC8=");
String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;
String url_tDBInput_3 = "jdbc:postgresql:
conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3, dbUser_tDBInput_3,
dbPwd_tDBInput_3);
conn_tDBInput_3.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();
String dbquery_tDBInput_3 = "SELECT * FROM dev_1.field_mappings_fields;";
globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
java.sql.ResultSet rs_tDBInput_3 = null;
try {
rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();
String tmpContent_tDBInput_3 = null;
while (rs_tDBInput_3.next()) {
nb_line_tDBInput_3++;
if (colQtyInRs_tDBInput_3 < 1) {
row1.field_mapping_id = 0;
} else {
row1.field_mapping_id = rs_tDBInput_3.getLong(1);
if (rs_tDBInput_3.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_3 < 2) {
row1.source_fields_id = 0;
} else {
row1.source_fields_id = rs_tDBInput_3.getLong(2);
if (rs_tDBInput_3.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
currentComponent = "tDBInput_3";
tos_count_tDBInput_3++;
currentComponent = "tDBInput_3";
currentComponent = "tMap_1";
if (execStat) {
runStat.updateStatOnConnection(iterateId, 1, 1
, "row1"
);
}
boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
boolean rejectedInnerJoin_tMap_1 = false;
boolean mainRowRejected_tMap_1 = false;
boolean forceLooprow2 = false;
row2Struct row2ObjectFromLookup = null;
if (!rejectedInnerJoin_tMap_1) {
hasCasePrimitiveKeyWithNull_tMap_1 = false;
Object exprKeyValue_row2__id = row1.field_mapping_id;
if (exprKeyValue_row2__id == null) {
hasCasePrimitiveKeyWithNull_tMap_1 = true;
} else {
row2HashKey.id = (long) (Long) exprKeyValue_row2__id;
}
row2HashKey.hashCodeDirty = true;
if (!hasCasePrimitiveKeyWithNull_tMap_1) {
tHash_Lookup_row2.lookup(row2HashKey);
}
}
if (tHash_Lookup_row2 != null && tHash_Lookup_row2.getCount(row2HashKey) > 1) {
}
row2Struct row2 = null;
row2Struct fromLookup_row2 = null;
row2 = row2Default;
if (tHash_Lookup_row2 != null && tHash_Lookup_row2.hasNext()) {
fromLookup_row2 = tHash_Lookup_row2.next();
}
if (fromLookup_row2 != null) {
row2 = fromLookup_row2;
}
boolean forceLooprow3 = false;
row3Struct row3ObjectFromLookup = null;
if (!rejectedInnerJoin_tMap_1) {
hasCasePrimitiveKeyWithNull_tMap_1 = false;
Object exprKeyValue_row3__id = row2.target_field_id;
if (exprKeyValue_row3__id == null) {
hasCasePrimitiveKeyWithNull_tMap_1 = true;
} else {
row3HashKey.id = (long) (Long) exprKeyValue_row3__id;
}
row3HashKey.hashCodeDirty = true;
if (!hasCasePrimitiveKeyWithNull_tMap_1) {
tHash_Lookup_row3.lookup(row3HashKey);
}
}
if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) {
}
row3Struct row3 = null;
row3Struct fromLookup_row3 = null;
row3 = row3Default;
if (tHash_Lookup_row3 != null && tHash_Lookup_row3.hasNext()) {
fromLookup_row3 = tHash_Lookup_row3.next();
}
if (fromLookup_row3 != null) {
row3 = fromLookup_row3;
}
{
Var__tMap_1__Struct Var = Var__tMap_1;
OutCSV = null;
OutCSV_tmp.name = row2.name;
OutCSV_tmp.expression = row2.expression;
OutCSV_tmp.name_1 = row3.name;
OutCSV_tmp.type = row3.type;
OutCSV_tmp.description = row3.description;
OutCSV = OutCSV_tmp;
}
rejectedInnerJoin_tMap_1 = false;
tos_count_tMap_1++;
currentComponent = "tMap_1";
if (OutCSV != null) {
currentComponent = "tFileOutputDelimited_1";
if (execStat) {
runStat.updateStatOnConnection(iterateId, 1, 1
, "OutCSV"
);
}
StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
if (OutCSV.name != null) {
sb_tFileOutputDelimited_1.append(OutCSV.name);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (OutCSV.expression != null) {
sb_tFileOutputDelimited_1.append(OutCSV.expression);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (OutCSV.name_1 != null) {
sb_tFileOutputDelimited_1.append(OutCSV.name_1);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (OutCSV.type != null) {
sb_tFileOutputDelimited_1.append(OutCSV.type);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (OutCSV.description != null) {
sb_tFileOutputDelimited_1.append(OutCSV.description);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
nb_line_tFileOutputDelimited_1++;
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());
tos_count_tFileOutputDelimited_1++;
currentComponent = "tFileOutputDelimited_1";
currentComponent = "tFileOutputDelimited_1";
}
currentComponent = "tMap_1";
currentComponent = "tDBInput_3";
currentComponent = "tDBInput_3";
}
} finally {
if (rs_tDBInput_3 != null) {
rs_tDBInput_3.close();
}
if (stmt_tDBInput_3 != null) {
stmt_tDBInput_3.close();
}
if (conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {
conn_tDBInput_3.commit();
conn_tDBInput_3.close();
if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
&& routines.system.BundleUtils.inOSGi()) {
Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
.getMethod("checkedShutdown").invoke(null, (Object[]) null);
}
}
}
globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);
ok_Hash.put("tDBInput_3", true);
end_Hash.put("tDBInput_3", System.currentTimeMillis());
currentComponent = "tMap_1";
if (tHash_Lookup_row2 != null) {
tHash_Lookup_row2.endGet();
}
globalMap.remove("tHash_Lookup_row2");
if (tHash_Lookup_row3 != null) {
tHash_Lookup_row3.endGet();
}
globalMap.remove("tHash_Lookup_row3");
if (execStat) {
runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
}
ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tFileOutputDelimited_1";
if (outtFileOutputDelimited_1 != null) {
outtFileOutputDelimited_1.flush();
outtFileOutputDelimited_1.close();
}
globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);
globalMap.put("tFileOutputDelimited_1_FILE_NAME", fileName_tFileOutputDelimited_1);
resourceMap.put("finish_tFileOutputDelimited_1", true);
if (execStat) {
runStat.updateStat(resourceMap, iterateId, 2, 0, "OutCSV");
}
ok_Hash.put("tFileOutputDelimited_1", true);
end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
globalMap.remove("tHash_Lookup_row3");
globalMap.remove("tHash_Lookup_row2");
try {
currentComponent = "tDBInput_3";
currentComponent = "tMap_1";
currentComponent = "tFileOutputDelimited_1";
if (resourceMap.get("finish_tFileOutputDelimited_1") == null) {
java.io.Writer outtFileOutputDelimited_1 = (java.io.Writer) resourceMap
.get("out_tFileOutputDelimited_1");
if (outtFileOutputDelimited_1 != null) {
outtFileOutputDelimited_1.flush();
outtFileOutputDelimited_1.close();
}
}
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
}
public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
final static byte[] commonByteArrayLock_TALYS_FieldMappingsLineage = new byte[0];
static byte[] commonByteArray_TALYS_FieldMappingsLineage = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long id;
public long getId() {
return this.id;
}
public String name;
public String getName() {
return this.name;
}
public String type;
public String getType() {
return this.type;
}
public String description;
public String getDescription() {
return this.description;
}
public Long structure_id;
public Long getStructure_id() {
return this.structure_id;
}
public Long parent_id;
public Long getParent_id() {
return this.parent_id;
}
public Boolean is_mandatory;
public Boolean getIs_mandatory() {
return this.is_mandatory;
}
public Boolean is_unique;
public Boolean getIs_unique() {
return this.is_unique;
}
public Boolean is_deleted;
public Boolean getIs_deleted() {
return this.is_deleted;
}
public String source_name;
public String getSource_name() {
return this.source_name;
}
public Long sourceid;
public Long getSourceid() {
return this.sourceid;
}
public Integer field_position;
public Integer getField_position() {
return this.field_position;
}
public Integer field_start;
public Integer getField_start() {
return this.field_start;
}
public Integer field_end;
public Integer getField_end() {
return this.field_end;
}
public Long previous_reference_id;
public Long getPrevious_reference_id() {
return this.previous_reference_id;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.id;
this.hashCode = result;
this.hashCodeDirty = false;
}
return this.hashCode;
}
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
final row3Struct other = (row3Struct) obj;
if (this.id != other.id)
return false;
return true;
}
public void copyDataTo(row3Struct other) {
other.id = this.id;
other.name = this.name;
other.type = this.type;
other.description = this.description;
other.structure_id = this.structure_id;
other.parent_id = this.parent_id;
other.is_mandatory = this.is_mandatory;
other.is_unique = this.is_unique;
other.is_deleted = this.is_deleted;
other.source_name = this.source_name;
other.sourceid = this.sourceid;
other.field_position = this.field_position;
other.field_start = this.field_start;
other.field_end = this.field_end;
other.previous_reference_id = this.previous_reference_id;
}
public void copyKeysDataTo(row3Struct other) {
other.id = this.id;
}
private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
dis.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
unmarshaller.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
Integer intReturn;
int length = 0;
length = dis.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = dis.readInt();
}
return intReturn;
}
private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
throws IOException {
Integer intReturn;
int length = 0;
length = unmarshaller.readByte();
if (length == -1) {
intReturn = null;
} else {
intReturn = unmarshaller.readInt();
}
return intReturn;
}
private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
if (intNum == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeInt(intNum);
}
}
private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
throws IOException {
if (intNum == null) {
marshaller.writeByte(-1);
} else {
marshaller.writeByte(0);
marshaller.writeInt(intNum);
}
}
public void readKeysData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeKeysData(ObjectOutputStream dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
try {
int length = 0;
this.name = readString(dis, ois);
this.type = readString(dis, ois);
this.description = readString(dis, ois);
length = dis.readByte();
if (length == -1) {
this.structure_id = null;
} else {
this.structure_id = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.parent_id = null;
} else {
this.parent_id = dis.readLong();
}
length = dis.readByte();
if (length == -1) {
this.is_mandatory = null;
} else {
this.is_mandatory = dis.readBoolean();
}
length = dis.readByte();
if (length == -1) {
this.is_unique = null;
} else {
this.is_unique = dis.readBoolean();
}
length = dis.readByte();
if (length == -1) {
this.is_deleted = null;
} else {
this.is_deleted = dis.readBoolean();
}
this.source_name = readString(dis, ois);
length = dis.readByte();
if (length == -1) {
this.sourceid = null;
} else {
this.sourceid = dis.readLong();
}
this.field_position = readInteger(dis, ois);
this.field_start = readInteger(dis, ois);
this.field_end = readInteger(dis, ois);
length = dis.readByte();
if (length == -1) {
this.previous_reference_id = null;
} else {
this.previous_reference_id = dis.readLong();
}
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
try {
int length = 0;
this.name = readString(dis, objectIn);
this.type = readString(dis, objectIn);
this.description = readString(dis, objectIn);
length = objectIn.readByte();
if (length == -1) {
this.structure_id = null;
} else {
this.structure_id = objectIn.readLong();
}
length = objectIn.readByte();
if (length == -1) {
this.parent_id = null;
} else {
this.parent_id = objectIn.readLong();
}
length = objectIn.readByte();
if (length == -1) {
this.is_mandatory = null;
} else {
this.is_mandatory = objectIn.readBoolean();
}
length = objectIn.readByte();
if (length == -1) {
this.is_unique = null;
} else {
this.is_unique = objectIn.readBoolean();
}
length = objectIn.readByte();
if (length == -1) {
this.is_deleted = null;
} else {
this.is_deleted = objectIn.readBoolean();
}
this.source_name = readString(dis, objectIn);
length = objectIn.readByte();
if (length == -1) {
this.sourceid = null;
} else {
this.sourceid = objectIn.readLong();
}
this.field_position = readInteger(dis, objectIn);
this.field_start = readInteger(dis, objectIn);
this.field_end = readInteger(dis, objectIn);
length = objectIn.readByte();
if (length == -1) {
this.previous_reference_id = null;
} else {
this.previous_reference_id = objectIn.readLong();
}
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
try {
writeString(this.name, dos, oos);
writeString(this.type, dos, oos);
writeString(this.description, dos, oos);
if (this.structure_id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.structure_id);
}
if (this.parent_id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.parent_id);
}
if (this.is_mandatory == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.is_mandatory);
}
if (this.is_unique == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.is_unique);
}
if (this.is_deleted == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.is_deleted);
}
writeString(this.source_name, dos, oos);
if (this.sourceid == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.sourceid);
}
writeInteger(this.field_position, dos, oos);
writeInteger(this.field_start, dos, oos);
writeInteger(this.field_end, dos, oos);
if (this.previous_reference_id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.previous_reference_id);
}
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
try {
writeString(this.name, dos, objectOut);
writeString(this.type, dos, objectOut);
writeString(this.description, dos, objectOut);
if (this.structure_id == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.structure_id);
}
if (this.parent_id == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.parent_id);
}
if (this.is_mandatory == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeBoolean(this.is_mandatory);
}
if (this.is_unique == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeBoolean(this.is_unique);
}
if (this.is_deleted == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeBoolean(this.is_deleted);
}
writeString(this.source_name, dos, objectOut);
if (this.sourceid == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.sourceid);
}
writeInteger(this.field_position, dos, objectOut);
writeInteger(this.field_start, dos, objectOut);
writeInteger(this.field_end, dos, objectOut);
if (this.previous_reference_id == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.previous_reference_id);
}
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public boolean supportMarshaller() {
return true;
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",type=" + type);
sb.append(",description=" + description);
sb.append(",structure_id=" + String.valueOf(structure_id));
sb.append(",parent_id=" + String.valueOf(parent_id));
sb.append(",is_mandatory=" + String.valueOf(is_mandatory));
sb.append(",is_unique=" + String.valueOf(is_unique));
sb.append(",is_deleted=" + String.valueOf(is_deleted));
sb.append(",source_name=" + source_name);
sb.append(",sourceid=" + String.valueOf(sourceid));
sb.append(",field_position=" + String.valueOf(field_position));
sb.append(",field_start=" + String.valueOf(field_start));
sb.append(",field_end=" + String.valueOf(field_end));
sb.append(",previous_reference_id=" + String.valueOf(previous_reference_id));
sb.append("]");
return sb.toString();
}
public int compareTo(row3Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.id, other.id);
if (returnValue != 0) {
return returnValue;
}
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);
final boolean execStat = this.execStat;
String iterateId = "";
String currentComponent = "";
java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
try {
boolean resumeIt = true;
if (globalResumeTicket == false && resumeEntryMethodName != null) {
String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
resumeIt = resumeEntryMethodName.equals(currentMethodName);
}
if (resumeIt || globalResumeTicket) {
globalResumeTicket = true;
row3Struct row3 = new row3Struct();
ok_Hash.put("tAdvancedHash_row3", false);
start_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row3";
if (execStat) {
runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
}
int tos_count_tAdvancedHash_row3 = 0;
org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
.<row3Struct>getLookup(matchingModeEnum_row3);
globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);
ok_Hash.put("tDBInput_1", false);
start_Hash.put("tDBInput_1", System.currentTimeMillis());
currentComponent = "tDBInput_1";
int tos_count_tDBInput_1 = 0;
int nb_line_tDBInput_1 = 0;
java.sql.Connection conn_tDBInput_1 = null;
String driverClass_tDBInput_1 = "org.postgresql.Driver";
java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
String dbUser_tDBInput_1 = "postgres";
final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil
.decryptPassword("enc:routine.encryption.key.v1:s1qlQstB8oCMEZigDtt3wlNEzY19KN421Rt9jT/rEL4=");
String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;
String url_tDBInput_1 = "jdbc:postgresql:
conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
dbPwd_tDBInput_1);
conn_tDBInput_1.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();
String dbquery_tDBInput_1 = "SELECT * FROM dev_1.fields;";
globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
java.sql.ResultSet rs_tDBInput_1 = null;
try {
rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();
String tmpContent_tDBInput_1 = null;
while (rs_tDBInput_1.next()) {
nb_line_tDBInput_1++;
if (colQtyInRs_tDBInput_1 < 1) {
row3.id = 0;
} else {
row3.id = rs_tDBInput_1.getLong(1);
if (rs_tDBInput_1.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_1 < 2) {
row3.name = null;
} else {
row3.name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
}
if (colQtyInRs_tDBInput_1 < 3) {
row3.type = null;
} else {
row3.type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
}
if (colQtyInRs_tDBInput_1 < 4) {
row3.description = null;
} else {
row3.description = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
}
if (colQtyInRs_tDBInput_1 < 5) {
row3.structure_id = null;
} else {
row3.structure_id = rs_tDBInput_1.getLong(5);
if (rs_tDBInput_1.wasNull()) {
row3.structure_id = null;
}
}
if (colQtyInRs_tDBInput_1 < 6) {
row3.parent_id = null;
} else {
row3.parent_id = rs_tDBInput_1.getLong(6);
if (rs_tDBInput_1.wasNull()) {
row3.parent_id = null;
}
}
if (colQtyInRs_tDBInput_1 < 7) {
row3.is_mandatory = null;
} else {
row3.is_mandatory = rs_tDBInput_1.getBoolean(7);
if (rs_tDBInput_1.wasNull()) {
row3.is_mandatory = null;
}
}
if (colQtyInRs_tDBInput_1 < 8) {
row3.is_unique = null;
} else {
row3.is_unique = rs_tDBInput_1.getBoolean(8);
if (rs_tDBInput_1.wasNull()) {
row3.is_unique = null;
}
}
if (colQtyInRs_tDBInput_1 < 9) {
row3.is_deleted = null;
} else {
row3.is_deleted = rs_tDBInput_1.getBoolean(9);
if (rs_tDBInput_1.wasNull()) {
row3.is_deleted = null;
}
}
if (colQtyInRs_tDBInput_1 < 10) {
row3.source_name = null;
} else {
row3.source_name = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
}
if (colQtyInRs_tDBInput_1 < 11) {
row3.sourceid = null;
} else {
row3.sourceid = rs_tDBInput_1.getLong(11);
if (rs_tDBInput_1.wasNull()) {
row3.sourceid = null;
}
}
if (colQtyInRs_tDBInput_1 < 12) {
row3.field_position = null;
} else {
row3.field_position = rs_tDBInput_1.getInt(12);
if (rs_tDBInput_1.wasNull()) {
row3.field_position = null;
}
}
if (colQtyInRs_tDBInput_1 < 13) {
row3.field_start = null;
} else {
row3.field_start = rs_tDBInput_1.getInt(13);
if (rs_tDBInput_1.wasNull()) {
row3.field_start = null;
}
}
if (colQtyInRs_tDBInput_1 < 14) {
row3.field_end = null;
} else {
row3.field_end = rs_tDBInput_1.getInt(14);
if (rs_tDBInput_1.wasNull()) {
row3.field_end = null;
}
}
if (colQtyInRs_tDBInput_1 < 15) {
row3.previous_reference_id = null;
} else {
row3.previous_reference_id = rs_tDBInput_1.getLong(15);
if (rs_tDBInput_1.wasNull()) {
row3.previous_reference_id = null;
}
}
currentComponent = "tDBInput_1";
tos_count_tDBInput_1++;
currentComponent = "tDBInput_1";
currentComponent = "tAdvancedHash_row3";
if (execStat) {
runStat.updateStatOnConnection(iterateId, 1, 1
, "row3"
);
}
row3Struct row3_HashRow = new row3Struct();
row3_HashRow.id = row3.id;
row3_HashRow.name = row3.name;
row3_HashRow.type = row3.type;
row3_HashRow.description = row3.description;
row3_HashRow.structure_id = row3.structure_id;
row3_HashRow.parent_id = row3.parent_id;
row3_HashRow.is_mandatory = row3.is_mandatory;
row3_HashRow.is_unique = row3.is_unique;
row3_HashRow.is_deleted = row3.is_deleted;
row3_HashRow.source_name = row3.source_name;
row3_HashRow.sourceid = row3.sourceid;
row3_HashRow.field_position = row3.field_position;
row3_HashRow.field_start = row3.field_start;
row3_HashRow.field_end = row3.field_end;
row3_HashRow.previous_reference_id = row3.previous_reference_id;
tHash_Lookup_row3.put(row3_HashRow);
tos_count_tAdvancedHash_row3++;
currentComponent = "tAdvancedHash_row3";
currentComponent = "tAdvancedHash_row3";
currentComponent = "tDBInput_1";
currentComponent = "tDBInput_1";
}
} finally {
if (rs_tDBInput_1 != null) {
rs_tDBInput_1.close();
}
if (stmt_tDBInput_1 != null) {
stmt_tDBInput_1.close();
}
if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {
conn_tDBInput_1.commit();
conn_tDBInput_1.close();
if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
&& routines.system.BundleUtils.inOSGi()) {
Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
.getMethod("checkedShutdown").invoke(null, (Object[]) null);
}
}
}
globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);
ok_Hash.put("tDBInput_1", true);
end_Hash.put("tDBInput_1", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row3";
tHash_Lookup_row3.endPut();
if (execStat) {
runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
}
ok_Hash.put("tAdvancedHash_row3", true);
end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "tDBInput_1";
currentComponent = "tAdvancedHash_row3";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
}
public static class row2Struct implements routines.system.IPersistableComparableLookupRow<row2Struct> {
final static byte[] commonByteArrayLock_TALYS_FieldMappingsLineage = new byte[0];
static byte[] commonByteArray_TALYS_FieldMappingsLineage = new byte[0];
protected static final int DEFAULT_HASHCODE = 1;
protected static final int PRIME = 31;
protected int hashCode = DEFAULT_HASHCODE;
public boolean hashCodeDirty = true;
public String loopKey;
public long id;
public long getId() {
return this.id;
}
public String name;
public String getName() {
return this.name;
}
public String expression;
public String getExpression() {
return this.expression;
}
public Boolean is_deleted;
public Boolean getIs_deleted() {
return this.is_deleted;
}
public Long target_field_id;
public Long getTarget_field_id() {
return this.target_field_id;
}
public String comment;
public String getComment() {
return this.comment;
}
@Override
public int hashCode() {
if (this.hashCodeDirty) {
final int prime = PRIME;
int result = DEFAULT_HASHCODE;
result = prime * result + (int) this.id;
this.hashCode = result;
this.hashCodeDirty = false;
}
return this.hashCode;
}
@Override
public boolean equals(Object obj) {
if (this == obj)
return true;
if (obj == null)
return false;
if (getClass() != obj.getClass())
return false;
final row2Struct other = (row2Struct) obj;
if (this.id != other.id)
return false;
return true;
}
public void copyDataTo(row2Struct other) {
other.id = this.id;
other.name = this.name;
other.expression = this.expression;
other.is_deleted = this.is_deleted;
other.target_field_id = this.target_field_id;
other.comment = this.comment;
}
public void copyKeysDataTo(row2Struct other) {
other.id = this.id;
}
private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
dis.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
throws IOException {
String strReturn = null;
int length = 0;
length = unmarshaller.readInt();
if (length == -1) {
strReturn = null;
} else {
byte[] byteArray = new byte[length];
unmarshaller.read(byteArray);
strReturn = new String(byteArray, utf8Charset);
}
return strReturn;
}
private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
throws IOException {
if (str == null) {
marshaller.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
marshaller.writeInt(byteArray.length);
marshaller.write(byteArray);
}
}
private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
if (str == null) {
dos.writeInt(-1);
} else {
byte[] byteArray = str.getBytes(utf8Charset);
dos.writeInt(byteArray.length);
dos.write(byteArray);
}
}
public void readKeysData(ObjectInputStream dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_TALYS_FieldMappingsLineage) {
try {
int length = 0;
this.id = dis.readLong();
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeKeysData(ObjectOutputStream dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
try {
dos.writeLong(this.id);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
try {
int length = 0;
this.name = readString(dis, ois);
this.expression = readString(dis, ois);
length = dis.readByte();
if (length == -1) {
this.is_deleted = null;
} else {
this.is_deleted = dis.readBoolean();
}
length = dis.readByte();
if (length == -1) {
this.target_field_id = null;
} else {
this.target_field_id = dis.readLong();
}
this.comment = readString(dis, ois);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
try {
int length = 0;
this.name = readString(dis, objectIn);
this.expression = readString(dis, objectIn);
length = objectIn.readByte();
if (length == -1) {
this.is_deleted = null;
} else {
this.is_deleted = objectIn.readBoolean();
}
length = objectIn.readByte();
if (length == -1) {
this.target_field_id = null;
} else {
this.target_field_id = objectIn.readLong();
}
this.comment = readString(dis, objectIn);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
try {
writeString(this.name, dos, oos);
writeString(this.expression, dos, oos);
if (this.is_deleted == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeBoolean(this.is_deleted);
}
if (this.target_field_id == null) {
dos.writeByte(-1);
} else {
dos.writeByte(0);
dos.writeLong(this.target_field_id);
}
writeString(this.comment, dos, oos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
try {
writeString(this.name, dos, objectOut);
writeString(this.expression, dos, objectOut);
if (this.is_deleted == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeBoolean(this.is_deleted);
}
if (this.target_field_id == null) {
objectOut.writeByte(-1);
} else {
objectOut.writeByte(0);
objectOut.writeLong(this.target_field_id);
}
writeString(this.comment, dos, objectOut);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public boolean supportMarshaller() {
return true;
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + String.valueOf(id));
sb.append(",name=" + name);
sb.append(",expression=" + expression);
sb.append(",is_deleted=" + String.valueOf(is_deleted));
sb.append(",target_field_id=" + String.valueOf(target_field_id));
sb.append(",comment=" + comment);
sb.append("]");
return sb.toString();
}
public int compareTo(row2Struct other) {
int returnValue = -1;
returnValue = checkNullsAndCompare(this.id, other.id);
if (returnValue != 0) {
return returnValue;
}
return returnValue;
}
private int checkNullsAndCompare(Object object1, Object object2) {
int returnValue = 0;
if (object1 instanceof Comparable && object2 instanceof Comparable) {
returnValue = ((Comparable) object1).compareTo(object2);
} else if (object1 != null && object2 != null) {
returnValue = compareStrings(object1.toString(), object2.toString());
} else if (object1 == null && object2 != null) {
returnValue = 1;
} else if (object1 != null && object2 == null) {
returnValue = -1;
} else {
returnValue = 0;
}
return returnValue;
}
private int compareStrings(String string1, String string2) {
return string1.compareTo(string2);
}
}
public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);
final boolean execStat = this.execStat;
String iterateId = "";
String currentComponent = "";
java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();
try {
boolean resumeIt = true;
if (globalResumeTicket == false && resumeEntryMethodName != null) {
String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
resumeIt = resumeEntryMethodName.equals(currentMethodName);
}
if (resumeIt || globalResumeTicket) {
globalResumeTicket = true;
row2Struct row2 = new row2Struct();
ok_Hash.put("tAdvancedHash_row2", false);
start_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row2";
if (execStat) {
runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
}
int tos_count_tAdvancedHash_row2 = 0;
org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row2 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;
org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row2Struct> tHash_Lookup_row2 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
.<row2Struct>getLookup(matchingModeEnum_row2);
globalMap.put("tHash_Lookup_row2", tHash_Lookup_row2);
ok_Hash.put("tDBInput_2", false);
start_Hash.put("tDBInput_2", System.currentTimeMillis());
currentComponent = "tDBInput_2";
int tos_count_tDBInput_2 = 0;
int nb_line_tDBInput_2 = 0;
java.sql.Connection conn_tDBInput_2 = null;
String driverClass_tDBInput_2 = "org.postgresql.Driver";
java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
String dbUser_tDBInput_2 = "postgres";
final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil
.decryptPassword("enc:routine.encryption.key.v1:p64NXsu05Gooa2BXSDC2cuQZrhUaYKE7AuM0MTJh0RU=");
String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;
String url_tDBInput_2 = "jdbc:postgresql:
conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
dbPwd_tDBInput_2);
conn_tDBInput_2.setAutoCommit(false);
java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();
String dbquery_tDBInput_2 = "SELECT * FROM dev_1.field_mappings;";
globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
java.sql.ResultSet rs_tDBInput_2 = null;
try {
rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();
String tmpContent_tDBInput_2 = null;
while (rs_tDBInput_2.next()) {
nb_line_tDBInput_2++;
if (colQtyInRs_tDBInput_2 < 1) {
row2.id = 0;
} else {
row2.id = rs_tDBInput_2.getLong(1);
if (rs_tDBInput_2.wasNull()) {
throw new RuntimeException("Null value in non-Nullable column");
}
}
if (colQtyInRs_tDBInput_2 < 2) {
row2.name = null;
} else {
row2.name = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
}
if (colQtyInRs_tDBInput_2 < 3) {
row2.expression = null;
} else {
row2.expression = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
}
if (colQtyInRs_tDBInput_2 < 4) {
row2.is_deleted = null;
} else {
row2.is_deleted = rs_tDBInput_2.getBoolean(4);
if (rs_tDBInput_2.wasNull()) {
row2.is_deleted = null;
}
}
if (colQtyInRs_tDBInput_2 < 5) {
row2.target_field_id = null;
} else {
row2.target_field_id = rs_tDBInput_2.getLong(5);
if (rs_tDBInput_2.wasNull()) {
row2.target_field_id = null;
}
}
if (colQtyInRs_tDBInput_2 < 6) {
row2.comment = null;
} else {
row2.comment = routines.system.JDBCUtil.getString(rs_tDBInput_2, 6, false);
}
currentComponent = "tDBInput_2";
tos_count_tDBInput_2++;
currentComponent = "tDBInput_2";
currentComponent = "tAdvancedHash_row2";
if (execStat) {
runStat.updateStatOnConnection(iterateId, 1, 1
, "row2"
);
}
row2Struct row2_HashRow = new row2Struct();
row2_HashRow.id = row2.id;
row2_HashRow.name = row2.name;
row2_HashRow.expression = row2.expression;
row2_HashRow.is_deleted = row2.is_deleted;
row2_HashRow.target_field_id = row2.target_field_id;
row2_HashRow.comment = row2.comment;
tHash_Lookup_row2.put(row2_HashRow);
tos_count_tAdvancedHash_row2++;
currentComponent = "tAdvancedHash_row2";
currentComponent = "tAdvancedHash_row2";
currentComponent = "tDBInput_2";
currentComponent = "tDBInput_2";
}
} finally {
if (rs_tDBInput_2 != null) {
rs_tDBInput_2.close();
}
if (stmt_tDBInput_2 != null) {
stmt_tDBInput_2.close();
}
if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {
conn_tDBInput_2.commit();
conn_tDBInput_2.close();
if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
&& routines.system.BundleUtils.inOSGi()) {
Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
.getMethod("checkedShutdown").invoke(null, (Object[]) null);
}
}
}
globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);
ok_Hash.put("tDBInput_2", true);
end_Hash.put("tDBInput_2", System.currentTimeMillis());
currentComponent = "tAdvancedHash_row2";
tHash_Lookup_row2.endPut();
if (execStat) {
runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
}
ok_Hash.put("tAdvancedHash_row2", true);
end_Hash.put("tAdvancedHash_row2", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "tDBInput_2";
currentComponent = "tAdvancedHash_row2";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
}
public String resuming_logs_dir_path = null;
public String resuming_checkpoint_path = null;
public String parent_part_launcher = null;
private String resumeEntryMethodName = null;
private boolean globalResumeTicket = false;
public boolean watch = false;
public Integer portStats = null;
public int portTraces = 4334;
public String clientHost;
public String defaultClientHost = "localhost";
public String contextStr = "Default";
public boolean isDefaultContext = true;
public String pid = "0";
public String rootPid = null;
public String fatherPid = null;
public String fatherNode = null;
public long startTime = 0;
public boolean isChildJob = false;
public String log4jLevel = "";
private boolean enableLogStash;
private boolean execStat = true;
private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
protected java.util.Map<String, String> initialValue() {
java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
threadRunResultMap.put("errorCode", null);
threadRunResultMap.put("status", "");
return threadRunResultMap;
};
};
protected PropertiesWithType context_param = new PropertiesWithType();
public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();
public String status = "";
public static void main(String[] args) {
final FieldMappingsLineage FieldMappingsLineageClass = new FieldMappingsLineage();
int exitCode = FieldMappingsLineageClass.runJobInTOS(args);
System.exit(exitCode);
}
public String[][] runJob(String[] args) {
int exitCode = runJobInTOS(args);
String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };
return bufferValue;
}
public boolean hastBufferOutputComponent() {
boolean hastBufferOutput = false;
return hastBufferOutput;
}
public int runJobInTOS(String[] args) {
status = "";
String lastStr = "";
for (String arg : args) {
if (arg.equalsIgnoreCase("--context_param")) {
lastStr = arg;
} else if (lastStr.equals("")) {
evalParam(arg);
} else {
evalParam(lastStr + " " + arg);
lastStr = "";
}
}
enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));
if (clientHost == null) {
clientHost = defaultClientHost;
}
if (pid == null || "0".equals(pid)) {
pid = TalendString.getAsciiRandomString(6);
}
if (rootPid == null) {
rootPid = pid;
}
if (fatherPid == null) {
fatherPid = pid;
} else {
isChildJob = true;
}
if (portStats != null) {
if (portStats < 0 || portStats > 65535) {
System.err.println("The statistics socket port " + portStats + " is invalid.");
execStat = false;
}
} else {
execStat = false;
}
boolean inOSGi = routines.system.BundleUtils.inOSGi();
if (inOSGi) {
java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
if (jobProperties != null && jobProperties.get("context") != null) {
contextStr = (String) jobProperties.get("context");
}
}
try {
java.io.InputStream inContext = FieldMappingsLineage.class.getClassLoader()
.getResourceAsStream("talys/fieldmappingslineage_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = FieldMappingsLineage.class.getClassLoader()
.getResourceAsStream("config/contexts/" + contextStr + ".properties");
}
if (inContext != null) {
try {
if (context != null && context.isEmpty()) {
defaultProps.load(inContext);
context = new ContextProperties(defaultProps);
}
} finally {
inContext.close();
}
} else if (!isDefaultContext) {
System.err.println("Could not find the context " + contextStr);
}
if (!context_param.isEmpty()) {
context.putAll(context_param);
for (Object key : context_param.keySet()) {
String context_key = key.toString();
String context_type = context_param.getContextType(context_key);
context.setContextType(context_key, context_type);
}
}
class ContextProcessing {
private void processContext_0() {
}
public void processAllContext() {
processContext_0();
}
}
new ContextProcessing().processAllContext();
} catch (java.io.IOException ie) {
System.err.println("Could not load context " + contextStr);
ie.printStackTrace();
}
if (parentContextMap != null && !parentContextMap.isEmpty()) {
}
resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);
List<String> parametersToEncrypt = new java.util.ArrayList<String>();
resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));
if (execStat) {
try {
runStat.openSocket(!isChildJob);
runStat.setAllPID(rootPid, fatherPid, pid, jobName);
runStat.startThreadStat(clientHost, portStats);
runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
} catch (java.io.IOException ioException) {
ioException.printStackTrace();
}
}
java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
globalMap.put("concurrentHashMap", concurrentHashMap);
long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
long endUsedMemory = 0;
long end = 0;
startTime = System.currentTimeMillis();
this.globalResumeTicket = true;
this.globalResumeTicket = false;
try {
errorCode = null;
tDBInput_3Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tDBInput_3) {
globalMap.put("tDBInput_3_SUBPROCESS_STATE", -1);
e_tDBInput_3.printStackTrace();
}
this.globalResumeTicket = true;
end = System.currentTimeMillis();
if (watch) {
System.out.println((end - startTime) + " milliseconds");
}
endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
if (false) {
System.out.println(
(endUsedMemory - startUsedMemory) + " bytes memory increase when running : FieldMappingsLineage");
}
if (execStat) {
runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
runStat.stopThreadStat();
}
int returnCode = 0;
if (errorCode == null) {
returnCode = status != null && status.equals("failure") ? 1 : 0;
} else {
returnCode = errorCode.intValue();
}
resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
"" + returnCode, "", "", "");
return returnCode;
}
public void destroy() {
}
private java.util.Map<String, Object> getSharedConnections4REST() {
java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();
return connections;
}
private void evalParam(String arg) {
if (arg.startsWith("--resuming_logs_dir_path")) {
resuming_logs_dir_path = arg.substring(25);
} else if (arg.startsWith("--resuming_checkpoint_path")) {
resuming_checkpoint_path = arg.substring(27);
} else if (arg.startsWith("--parent_part_launcher")) {
parent_part_launcher = arg.substring(23);
} else if (arg.startsWith("--watch")) {
watch = true;
} else if (arg.startsWith("--stat_port=")) {
String portStatsStr = arg.substring(12);
if (portStatsStr != null && !portStatsStr.equals("null")) {
portStats = Integer.parseInt(portStatsStr);
}
} else if (arg.startsWith("--trace_port=")) {
portTraces = Integer.parseInt(arg.substring(13));
} else if (arg.startsWith("--client_host=")) {
clientHost = arg.substring(14);
} else if (arg.startsWith("--context=")) {
contextStr = arg.substring(10);
isDefaultContext = false;
} else if (arg.startsWith("--father_pid=")) {
fatherPid = arg.substring(13);
} else if (arg.startsWith("--root_pid=")) {
rootPid = arg.substring(11);
} else if (arg.startsWith("--father_node=")) {
fatherNode = arg.substring(14);
} else if (arg.startsWith("--pid=")) {
pid = arg.substring(6);
} else if (arg.startsWith("--context_type")) {
String keyValue = arg.substring(15);
int index = -1;
if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
if (fatherPid == null) {
context_param.setContextType(keyValue.substring(0, index),
replaceEscapeChars(keyValue.substring(index + 1)));
} else {
context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
}
}
} else if (arg.startsWith("--context_param")) {
String keyValue = arg.substring(16);
int index = -1;
if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
if (fatherPid == null) {
context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
} else {
context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
}
}
} else if (arg.startsWith("--log4jLevel=")) {
log4jLevel = arg.substring(13);
} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {
final int equal = arg.indexOf('=');
final String key = arg.substring("--".length(), equal);
System.setProperty(key, arg.substring(equal + 1));
}
}
private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";
private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };
private String replaceEscapeChars(String keyValue) {
if (keyValue == null || ("").equals(keyValue.trim())) {
return keyValue;
}
StringBuilder result = new StringBuilder();
int currIndex = 0;
while (currIndex < keyValue.length()) {
int index = -1;
for (String[] strArray : escapeChars) {
index = keyValue.indexOf(strArray[0], currIndex);
if (index >= 0) {
result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
strArray[1]));
currIndex = index + strArray[0].length();
break;
}
}
if (index < 0) {
result.append(keyValue.substring(currIndex));
currIndex = currIndex + keyValue.length();
}
}
return result.toString();
}
public Integer getErrorCode() {
return errorCode;
}
public String getStatus() {
return status;
}
ResumeUtil resumeUtil = null;
}