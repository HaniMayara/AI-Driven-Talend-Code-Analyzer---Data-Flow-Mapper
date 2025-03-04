package migrationubci.xml_to_xml_0_1;
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
public class xml_to_xml implements TalendJob {
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
private final String jobName = "xml_to_xml";
private final String projectName = "MIGRATIONUBCI";
public Integer errorCode = null;
private String currentComponent = "";
private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
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
xml_to_xml.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(xml_to_xml.this, new Object[] { e, currentComponent, globalMap });
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
public void tFileInputXML_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputXML_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputXML_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputXML_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputXML_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileInputXML_1_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class xmlStruct implements routines.system.IPersistableRow<xmlStruct> {
final static byte[] commonByteArrayLock_MIGRATIONUBCI_xml_to_xml = new byte[0];
static byte[] commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[0];
public String dept;
public String getDept() {
return this.dept;
}
public String name;
public String getName() {
return this.name;
}
public String salary;
public String getSalary() {
return this.salary;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_MIGRATIONUBCI_xml_to_xml.length) {
if (length < 1024 && commonByteArray_MIGRATIONUBCI_xml_to_xml.length == 0) {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[1024];
} else {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[2 * length];
}
}
dis.readFully(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length);
strReturn = new String(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length, utf8Charset);
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
if (length > commonByteArray_MIGRATIONUBCI_xml_to_xml.length) {
if (length < 1024 && commonByteArray_MIGRATIONUBCI_xml_to_xml.length == 0) {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[1024];
} else {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length);
strReturn = new String(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_MIGRATIONUBCI_xml_to_xml) {
try {
int length = 0;
this.dept = readString(dis);
this.name = readString(dis);
this.salary = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_MIGRATIONUBCI_xml_to_xml) {
try {
int length = 0;
this.dept = readString(dis);
this.name = readString(dis);
this.salary = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.dept, dos);
writeString(this.name, dos);
writeString(this.salary, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.dept, dos);
writeString(this.name, dos);
writeString(this.salary, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("dept=" + dept);
sb.append(",name=" + name);
sb.append(",salary=" + salary);
sb.append("]");
return sb.toString();
}
public int compareTo(xmlStruct other) {
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
final static byte[] commonByteArrayLock_MIGRATIONUBCI_xml_to_xml = new byte[0];
static byte[] commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[0];
public String dept;
public String getDept() {
return this.dept;
}
public String name;
public String getName() {
return this.name;
}
public String salary;
public String getSalary() {
return this.salary;
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_MIGRATIONUBCI_xml_to_xml.length) {
if (length < 1024 && commonByteArray_MIGRATIONUBCI_xml_to_xml.length == 0) {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[1024];
} else {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[2 * length];
}
}
dis.readFully(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length);
strReturn = new String(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length, utf8Charset);
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
if (length > commonByteArray_MIGRATIONUBCI_xml_to_xml.length) {
if (length < 1024 && commonByteArray_MIGRATIONUBCI_xml_to_xml.length == 0) {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[1024];
} else {
commonByteArray_MIGRATIONUBCI_xml_to_xml = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length);
strReturn = new String(commonByteArray_MIGRATIONUBCI_xml_to_xml, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_MIGRATIONUBCI_xml_to_xml) {
try {
int length = 0;
this.dept = readString(dis);
this.name = readString(dis);
this.salary = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_MIGRATIONUBCI_xml_to_xml) {
try {
int length = 0;
this.dept = readString(dis);
this.name = readString(dis);
this.salary = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.dept, dos);
writeString(this.name, dos);
writeString(this.salary, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.dept, dos);
writeString(this.name, dos);
writeString(this.salary, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("dept=" + dept);
sb.append(",name=" + name);
sb.append(",salary=" + salary);
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
public void tFileInputXML_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 0);
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
row1Struct row1 = new row1Struct();
xmlStruct xml = new xmlStruct();
ok_Hash.put("tFileOutputXML_1", false);
start_Hash.put("tFileOutputXML_1", System.currentTimeMillis());
currentComponent = "tFileOutputXML_1";
int tos_count_tFileOutputXML_1 = 0;
String originalFileName_tFileOutputXML_1 = "C:/Program Files (x86)/TOS_DI-8.0.1/studio/workspace/out.xml";
java.io.File originalFile_tFileOutputXML_1 = new java.io.File(originalFileName_tFileOutputXML_1);
String fileName_tFileOutputXML_1 = originalFileName_tFileOutputXML_1;
java.io.File file_tFileOutputXML_1 = new java.io.File(fileName_tFileOutputXML_1);
if (!file_tFileOutputXML_1.isAbsolute()) {
file_tFileOutputXML_1 = file_tFileOutputXML_1.getCanonicalFile();
}
file_tFileOutputXML_1.getParentFile().mkdirs();
String[] headers_tFileOutputXML_1 = new String[2];
headers_tFileOutputXML_1[0] = "<?xml version=\"1.0\" encoding=\"" + "ISO-8859-15" + "\"?>";
String[] footers_tFileOutputXML_1 = new String[1];
headers_tFileOutputXML_1[1] = "<" + "root" + ">";
footers_tFileOutputXML_1[0] = "</" + "root" + ">";
int nb_line_tFileOutputXML_1 = 0;
java.io.BufferedWriter out_tFileOutputXML_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
new java.io.FileOutputStream(file_tFileOutputXML_1), "ISO-8859-15"));
out_tFileOutputXML_1.write(headers_tFileOutputXML_1[0]);
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.write(headers_tFileOutputXML_1[1]);
out_tFileOutputXML_1.newLine();
ok_Hash.put("tMap_1", false);
start_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tMap_1";
int tos_count_tMap_1 = 0;
class Var__tMap_1__Struct {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
xmlStruct xml_tmp = new xmlStruct();
ok_Hash.put("tFileInputXML_1", false);
start_Hash.put("tFileInputXML_1", System.currentTimeMillis());
currentComponent = "tFileInputXML_1";
int tos_count_tFileInputXML_1 = 0;
int nb_line_tFileInputXML_1 = 0;
String os_tFileInputXML_1 = System.getProperty("os.name").toLowerCase();
boolean isWindows_tFileInputXML_1 = false;
if (os_tFileInputXML_1.indexOf("windows") > -1 || os_tFileInputXML_1.indexOf("nt") > -1) {
isWindows_tFileInputXML_1 = true;
}
class NameSpaceTool_tFileInputXML_1 {
public java.util.HashMap<String, String> xmlNameSpaceMap = new java.util.HashMap<String, String>();
private java.util.List<String> defualtNSPath = new java.util.ArrayList<String>();
public void countNSMap(org.dom4j.Element el) {
for (org.dom4j.Namespace ns : (java.util.List<org.dom4j.Namespace>) el.declaredNamespaces()) {
if (ns.getPrefix().trim().length() == 0) {
xmlNameSpaceMap.put("pre" + defualtNSPath.size(), ns.getURI());
String path = "";
org.dom4j.Element elTmp = el;
while (elTmp != null) {
if (elTmp.getNamespacePrefix() != null && elTmp.getNamespacePrefix().length() > 0) {
path = "/" + elTmp.getNamespacePrefix() + ":" + elTmp.getName() + path;
} else {
path = "/" + elTmp.getName() + path;
}
elTmp = elTmp.getParent();
}
defualtNSPath.add(path);
} else {
xmlNameSpaceMap.put(ns.getPrefix(), ns.getURI());
}
}
for (org.dom4j.Element e : (java.util.List<org.dom4j.Element>) el.elements()) {
countNSMap(e);
}
}
private final org.talend.xpath.XPathUtil util = new org.talend.xpath.XPathUtil();
{
util.setDefaultNSPath(defualtNSPath);
}
public String addDefaultNSPrefix(String path) {
return util.addDefaultNSPrefix(path);
}
public String addDefaultNSPrefix(String relativeXpression, String basePath) {
return util.addDefaultNSPrefix(relativeXpression, basePath);
}
}
class XML_API_tFileInputXML_1 {
public boolean isDefNull(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
if (node != null && node instanceof org.dom4j.Element) {
org.dom4j.Attribute attri = ((org.dom4j.Element) node).attribute("nil");
if (attri != null && ("true").equals(attri.getText())) {
return true;
}
}
return false;
}
public boolean isMissing(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
return node == null ? true : false;
}
public boolean isEmpty(org.dom4j.Node node) throws javax.xml.transform.TransformerException {
if (node != null) {
return node.getText().length() == 0;
}
return false;
}
}
org.dom4j.io.SAXReader reader_tFileInputXML_1 = new org.dom4j.io.SAXReader();
Object filename_tFileInputXML_1 = null;
try {
filename_tFileInputXML_1 = "D:/OneDrive - Talys Consulting/Bureau/déclaration 1.xml";
} catch (java.lang.Exception e) {
globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());
System.err.println(e.getMessage());
}
if (filename_tFileInputXML_1 != null && filename_tFileInputXML_1 instanceof String
&& filename_tFileInputXML_1.toString().startsWith("
if (!isWindows_tFileInputXML_1) {
filename_tFileInputXML_1 = filename_tFileInputXML_1.toString().replaceFirst("
}
}
boolean isValidFile_tFileInputXML_1 = true;
org.dom4j.Document doc_tFileInputXML_1 = null;
java.io.Closeable toClose_tFileInputXML_1 = null;
try {
if (filename_tFileInputXML_1 instanceof java.io.InputStream) {
java.io.InputStream inputStream_tFileInputXML_1 = (java.io.InputStream) filename_tFileInputXML_1;
toClose_tFileInputXML_1 = inputStream_tFileInputXML_1;
doc_tFileInputXML_1 = reader_tFileInputXML_1.read(inputStream_tFileInputXML_1);
} else {
java.io.Reader unicodeReader_tFileInputXML_1 = new UnicodeReader(
new java.io.FileInputStream(String.valueOf(filename_tFileInputXML_1)), "UTF-8");
toClose_tFileInputXML_1 = unicodeReader_tFileInputXML_1;
org.xml.sax.InputSource in_tFileInputXML_1 = new org.xml.sax.InputSource(
unicodeReader_tFileInputXML_1);
doc_tFileInputXML_1 = reader_tFileInputXML_1.read(in_tFileInputXML_1);
}
} catch (java.lang.Exception e) {
globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());
System.err.println(e.getMessage());
isValidFile_tFileInputXML_1 = false;
} finally {
if (toClose_tFileInputXML_1 != null) {
toClose_tFileInputXML_1.close();
}
}
if (isValidFile_tFileInputXML_1) {
NameSpaceTool_tFileInputXML_1 nsTool_tFileInputXML_1 = new NameSpaceTool_tFileInputXML_1();
nsTool_tFileInputXML_1.countNSMap(doc_tFileInputXML_1.getRootElement());
java.util.HashMap<String, String> xmlNameSpaceMap_tFileInputXML_1 = nsTool_tFileInputXML_1.xmlNameSpaceMap;
org.dom4j.XPath x_tFileInputXML_1 = doc_tFileInputXML_1
.createXPath(nsTool_tFileInputXML_1.addDefaultNSPrefix("root/employee"));
x_tFileInputXML_1.setNamespaceURIs(xmlNameSpaceMap_tFileInputXML_1);
java.util.List<org.dom4j.Node> nodeList_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) x_tFileInputXML_1
.selectNodes(doc_tFileInputXML_1);
XML_API_tFileInputXML_1 xml_api_tFileInputXML_1 = new XML_API_tFileInputXML_1();
String str_tFileInputXML_1 = "";
org.dom4j.Node node_tFileInputXML_1 = null;
java.util.Map<Integer, org.dom4j.XPath> xpaths_tFileInputXML_1 = new java.util.HashMap<Integer, org.dom4j.XPath>();
class XPathUtil_tFileInputXML_1 {
public void initXPaths_0(java.util.Map<Integer, org.dom4j.XPath> xpaths,
NameSpaceTool_tFileInputXML_1 nsTool,
java.util.HashMap<String, String> xmlNameSpaceMap) {
org.dom4j.XPath xpath_0 = org.dom4j.DocumentHelper
.createXPath(nsTool.addDefaultNSPrefix("../dept", "root/employee"));
xpath_0.setNamespaceURIs(xmlNameSpaceMap);
xpaths.put(0, xpath_0);
org.dom4j.XPath xpath_1 = org.dom4j.DocumentHelper
.createXPath(nsTool.addDefaultNSPrefix("name", "root/employee"));
xpath_1.setNamespaceURIs(xmlNameSpaceMap);
xpaths.put(1, xpath_1);
org.dom4j.XPath xpath_2 = org.dom4j.DocumentHelper
.createXPath(nsTool.addDefaultNSPrefix("salary", "root/employee"));
xpath_2.setNamespaceURIs(xmlNameSpaceMap);
xpaths.put(2, xpath_2);
}
public void initXPaths(java.util.Map<Integer, org.dom4j.XPath> xpaths,
NameSpaceTool_tFileInputXML_1 nsTool,
java.util.HashMap<String, String> xmlNameSpaceMap) {
initXPaths_0(xpaths, nsTool, xmlNameSpaceMap);
}
}
XPathUtil_tFileInputXML_1 xPathUtil_tFileInputXML_1 = new XPathUtil_tFileInputXML_1();
xPathUtil_tFileInputXML_1.initXPaths(xpaths_tFileInputXML_1, nsTool_tFileInputXML_1,
xmlNameSpaceMap_tFileInputXML_1);
for (org.dom4j.Node temp_tFileInputXML_1 : nodeList_tFileInputXML_1) {
nb_line_tFileInputXML_1++;
row1 = null;
boolean whetherReject_tFileInputXML_1 = false;
row1 = new row1Struct();
try {
Object obj0_tFileInputXML_1 = xpaths_tFileInputXML_1.get(0).evaluate(temp_tFileInputXML_1);
if (obj0_tFileInputXML_1 == null) {
node_tFileInputXML_1 = null;
str_tFileInputXML_1 = "";
} else if (obj0_tFileInputXML_1 instanceof org.dom4j.Node) {
node_tFileInputXML_1 = (org.dom4j.Node) obj0_tFileInputXML_1;
str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
} else if (obj0_tFileInputXML_1 instanceof String
|| obj0_tFileInputXML_1 instanceof Number) {
node_tFileInputXML_1 = temp_tFileInputXML_1;
str_tFileInputXML_1 = String.valueOf(obj0_tFileInputXML_1);
} else if (obj0_tFileInputXML_1 instanceof java.util.List) {
java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj0_tFileInputXML_1;
node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
: null;
str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
}
if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
row1.dept = null;
} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
row1.dept = "";
} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
row1.dept = null;
} else {
row1.dept = str_tFileInputXML_1;
}
Object obj1_tFileInputXML_1 = xpaths_tFileInputXML_1.get(1).evaluate(temp_tFileInputXML_1);
if (obj1_tFileInputXML_1 == null) {
node_tFileInputXML_1 = null;
str_tFileInputXML_1 = "";
} else if (obj1_tFileInputXML_1 instanceof org.dom4j.Node) {
node_tFileInputXML_1 = (org.dom4j.Node) obj1_tFileInputXML_1;
str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
} else if (obj1_tFileInputXML_1 instanceof String
|| obj1_tFileInputXML_1 instanceof Number) {
node_tFileInputXML_1 = temp_tFileInputXML_1;
str_tFileInputXML_1 = String.valueOf(obj1_tFileInputXML_1);
} else if (obj1_tFileInputXML_1 instanceof java.util.List) {
java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj1_tFileInputXML_1;
node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
: null;
str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
}
if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
row1.name = null;
} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
row1.name = "";
} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
row1.name = null;
} else {
row1.name = str_tFileInputXML_1;
}
Object obj2_tFileInputXML_1 = xpaths_tFileInputXML_1.get(2).evaluate(temp_tFileInputXML_1);
if (obj2_tFileInputXML_1 == null) {
node_tFileInputXML_1 = null;
str_tFileInputXML_1 = "";
} else if (obj2_tFileInputXML_1 instanceof org.dom4j.Node) {
node_tFileInputXML_1 = (org.dom4j.Node) obj2_tFileInputXML_1;
str_tFileInputXML_1 = org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
} else if (obj2_tFileInputXML_1 instanceof String
|| obj2_tFileInputXML_1 instanceof Number) {
node_tFileInputXML_1 = temp_tFileInputXML_1;
str_tFileInputXML_1 = String.valueOf(obj2_tFileInputXML_1);
} else if (obj2_tFileInputXML_1 instanceof java.util.List) {
java.util.List<org.dom4j.Node> nodes_tFileInputXML_1 = (java.util.List<org.dom4j.Node>) obj2_tFileInputXML_1;
node_tFileInputXML_1 = nodes_tFileInputXML_1.size() > 0 ? nodes_tFileInputXML_1.get(0)
: null;
str_tFileInputXML_1 = node_tFileInputXML_1 == null ? ""
: org.jaxen.function.StringFunction.evaluate(node_tFileInputXML_1,
org.jaxen.dom4j.DocumentNavigator.getInstance());
}
if (xml_api_tFileInputXML_1.isDefNull(node_tFileInputXML_1)) {
row1.salary = null;
} else if (xml_api_tFileInputXML_1.isEmpty(node_tFileInputXML_1)) {
row1.salary = "";
} else if (xml_api_tFileInputXML_1.isMissing(node_tFileInputXML_1)) {
row1.salary = null;
} else {
row1.salary = str_tFileInputXML_1;
}
} catch (java.lang.Exception e) {
globalMap.put("tFileInputXML_1_ERROR_MESSAGE", e.getMessage());
whetherReject_tFileInputXML_1 = true;
System.err.println(e.getMessage());
row1 = null;
}
currentComponent = "tFileInputXML_1";
tos_count_tFileInputXML_1++;
currentComponent = "tFileInputXML_1";
if (row1 != null) {
currentComponent = "tMap_1";
boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
boolean rejectedInnerJoin_tMap_1 = false;
boolean mainRowRejected_tMap_1 = false;
{
Var__tMap_1__Struct Var = Var__tMap_1;
xml = null;
xml_tmp.dept = row1.dept;
xml_tmp.name = row1.name;
xml_tmp.salary = row1.salary;
xml = xml_tmp;
}
rejectedInnerJoin_tMap_1 = false;
tos_count_tMap_1++;
currentComponent = "tMap_1";
if (xml != null) {
currentComponent = "tFileOutputXML_1";
StringBuilder tempRes_tFileOutputXML_1 = new StringBuilder("<" + "row");
tempRes_tFileOutputXML_1.append(">");
out_tFileOutputXML_1.write(tempRes_tFileOutputXML_1.toString());
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.write("<" + "dept" + ">"
+ ((xml.dept == null) ? "" : (TalendString.checkCDATAForXML(xml.dept))) + "</"
+ "dept" + ">");
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.write("<" + "name" + ">"
+ ((xml.name == null) ? "" : (TalendString.checkCDATAForXML(xml.name))) + "</"
+ "name" + ">");
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.write("<" + "salary" + ">"
+ ((xml.salary == null) ? "" : (TalendString.checkCDATAForXML(xml.salary)))
+ "</" + "salary" + ">");
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.write("</" + "row" + ">");
out_tFileOutputXML_1.newLine();
nb_line_tFileOutputXML_1++;
tos_count_tFileOutputXML_1++;
currentComponent = "tFileOutputXML_1";
currentComponent = "tFileOutputXML_1";
}
currentComponent = "tMap_1";
}
currentComponent = "tFileInputXML_1";
currentComponent = "tFileInputXML_1";
}
}
globalMap.put("tFileInputXML_1_NB_LINE", nb_line_tFileInputXML_1);
ok_Hash.put("tFileInputXML_1", true);
end_Hash.put("tFileInputXML_1", System.currentTimeMillis());
currentComponent = "tMap_1";
ok_Hash.put("tMap_1", true);
end_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tFileOutputXML_1";
out_tFileOutputXML_1.write(footers_tFileOutputXML_1[0]);
out_tFileOutputXML_1.newLine();
out_tFileOutputXML_1.close();
globalMap.put("tFileOutputXML_1_NB_LINE", nb_line_tFileOutputXML_1);
ok_Hash.put("tFileOutputXML_1", true);
end_Hash.put("tFileOutputXML_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
throw error;
} finally {
try {
currentComponent = "tFileInputXML_1";
currentComponent = "tMap_1";
currentComponent = "tFileOutputXML_1";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", 1);
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
final xml_to_xml xml_to_xmlClass = new xml_to_xml();
int exitCode = xml_to_xmlClass.runJobInTOS(args);
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
boolean inOSGi = routines.system.BundleUtils.inOSGi();
if (inOSGi) {
java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
if (jobProperties != null && jobProperties.get("context") != null) {
contextStr = (String) jobProperties.get("context");
}
}
try {
java.io.InputStream inContext = xml_to_xml.class.getClassLoader()
.getResourceAsStream("migrationubci/xml_to_xml_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = xml_to_xml.class.getClassLoader()
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
tFileInputXML_1Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tFileInputXML_1) {
globalMap.put("tFileInputXML_1_SUBPROCESS_STATE", -1);
e_tFileInputXML_1.printStackTrace();
}
this.globalResumeTicket = true;
end = System.currentTimeMillis();
if (watch) {
System.out.println((end - startTime) + " milliseconds");
}
endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
if (false) {
System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : xml_to_xml");
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