package formation.pos_to_csv_0_1;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.DataQuality;
import routines.Relational;
import routines.DataQualityDependencies;
import routines.Mathematical;
import routines.SQLike;
import routines.Numeric;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.DQTechnical;
import routines.StringHandling;
import routines.DataMasking;
import routines.TalendDate;
import routines.DqStringHandling;
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
public class Pos_to_csv implements TalendJob {
static {
System.setProperty("TalendJob.log", "Pos_to_csv.log");
}
private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager
.getLogger(Pos_to_csv.class);
protected static void logIgnoredError(String message, Throwable cause) {
log.error(message, cause);
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
private final String jobName = "Pos_to_csv";
private final String projectName = "FORMATION";
public Integer errorCode = null;
private String currentComponent = "";
private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();
private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();
private final JobStructureCatcherUtils talendJobLog = new JobStructureCatcherUtils(jobName,
"_bp9zkOlKEe--ErwZAdiA8w", "0.1");
private org.talend.job.audit.JobAuditLogger auditLogger_talendJobLog = null;
private RunStat runStat = new RunStat(talendJobLog, System.getProperty("audit.interval"));
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
Pos_to_csv.this.exception = e;
}
}
if (!(e instanceof TalendException)) {
try {
for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
if (m.getName().compareTo(currentComponent + "_error") == 0) {
m.invoke(Pos_to_csv.this, new Object[] { e, currentComponent, globalMap });
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
public void tFileInputPositional_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputPositional_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputPositional_1_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileOutputDelimited_1_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
tFileInputPositional_1_onSubJobError(exception, errorComponent, globalMap);
}
public void talendJobLog_error(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
end_Hash.put(errorComponent, System.currentTimeMillis());
status = "failure";
talendJobLog_onSubJobError(exception, errorComponent, globalMap);
}
public void tFileInputPositional_1_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public void talendJobLog_onSubJobError(Exception exception, String errorComponent,
final java.util.Map<String, Object> globalMap) throws TalendException {
resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");
}
public static class ContextBean {
static String evaluate(String context, String contextExpression, String jobName)
throws IOException, javax.script.ScriptException {
String currentContext = context;
String jobNameStripped = jobName.substring(jobName.lastIndexOf(".") + 1);
boolean inOSGi = routines.system.BundleUtils.inOSGi();
java.util.Dictionary<String, Object> jobProperties = null;
if (inOSGi) {
jobProperties = routines.system.BundleUtils.getJobProperties(jobNameStripped);
if (jobProperties != null && null != jobProperties.get("context")) {
currentContext = (String) jobProperties.get("context");
}
}
boolean isExpression = contextExpression.contains("+") || contextExpression.contains("(");
final String prefix = isExpression ? "\"" : "";
java.util.Properties defaultProps = new java.util.Properties();
java.io.InputStream inContext = Pos_to_csv.class.getClassLoader()
.getResourceAsStream("formation/pos_to_csv_0_1/contexts/" + currentContext + ".properties");
if (inContext == null) {
inContext = Pos_to_csv.class.getClassLoader()
.getResourceAsStream("config/contexts/" + currentContext + ".properties");
}
try {
defaultProps.load(inContext);
if (jobProperties != null) {
java.util.Enumeration<String> keys = jobProperties.keys();
while (keys.hasMoreElements()) {
String propKey = keys.nextElement();
if (defaultProps.containsKey(propKey)) {
defaultProps.put(propKey, (String) jobProperties.get(propKey));
}
}
}
} finally {
inContext.close();
}
java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("context.([\\w]+)");
java.util.regex.Matcher matcher = pattern.matcher(contextExpression);
while (matcher.find()) {
contextExpression = contextExpression.replaceAll(matcher.group(0),
prefix + defaultProps.getProperty(matcher.group(1)) + prefix);
}
if (contextExpression.startsWith("/services")) {
contextExpression = contextExpression.replaceFirst("/services", "");
}
return isExpression ? evaluateContextExpression(contextExpression) : contextExpression;
}
public static String evaluateContextExpression(String expression) throws javax.script.ScriptException {
javax.script.ScriptEngineManager manager = new javax.script.ScriptEngineManager();
javax.script.ScriptEngine engine = manager.getEngineByName("nashorn");
expression = expression.replaceAll("System.getProperty", "java.lang.System.getProperty");
return engine.eval(expression).toString();
}
public static String getContext(String context, String contextName, String jobName) throws Exception {
return contextName.contains("context.") ? evaluate(context, contextName, jobName) : contextName;
}
}
public static class outStruct implements routines.system.IPersistableRow<outStruct> {
final static byte[] commonByteArrayLock_FORMATION_Pos_to_csv = new byte[0];
static byte[] commonByteArray_FORMATION_Pos_to_csv = new byte[0];
public String id;
public String getId() {
return this.id;
}
public Boolean idIsNullable() {
return true;
}
public Boolean idIsKey() {
return false;
}
public Integer idLength() {
return null;
}
public Integer idPrecision() {
return null;
}
public String idDefault() {
return null;
}
public String idComment() {
return "";
}
public String idPattern() {
return "";
}
public String idOriginalDbColumnName() {
return "id";
}
public String nom;
public String getNom() {
return this.nom;
}
public Boolean nomIsNullable() {
return true;
}
public Boolean nomIsKey() {
return false;
}
public Integer nomLength() {
return null;
}
public Integer nomPrecision() {
return null;
}
public String nomDefault() {
return null;
}
public String nomComment() {
return "";
}
public String nomPattern() {
return "";
}
public String nomOriginalDbColumnName() {
return "nom";
}
public String prenom;
public String getPrenom() {
return this.prenom;
}
public Boolean prenomIsNullable() {
return true;
}
public Boolean prenomIsKey() {
return false;
}
public Integer prenomLength() {
return null;
}
public Integer prenomPrecision() {
return null;
}
public String prenomDefault() {
return null;
}
public String prenomComment() {
return "";
}
public String prenomPattern() {
return "";
}
public String prenomOriginalDbColumnName() {
return "prenom";
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_FORMATION_Pos_to_csv.length) {
if (length < 1024 && commonByteArray_FORMATION_Pos_to_csv.length == 0) {
commonByteArray_FORMATION_Pos_to_csv = new byte[1024];
} else {
commonByteArray_FORMATION_Pos_to_csv = new byte[2 * length];
}
}
dis.readFully(commonByteArray_FORMATION_Pos_to_csv, 0, length);
strReturn = new String(commonByteArray_FORMATION_Pos_to_csv, 0, length, utf8Charset);
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
if (length > commonByteArray_FORMATION_Pos_to_csv.length) {
if (length < 1024 && commonByteArray_FORMATION_Pos_to_csv.length == 0) {
commonByteArray_FORMATION_Pos_to_csv = new byte[1024];
} else {
commonByteArray_FORMATION_Pos_to_csv = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_FORMATION_Pos_to_csv, 0, length);
strReturn = new String(commonByteArray_FORMATION_Pos_to_csv, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_FORMATION_Pos_to_csv) {
try {
int length = 0;
this.id = readString(dis);
this.nom = readString(dis);
this.prenom = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_FORMATION_Pos_to_csv) {
try {
int length = 0;
this.id = readString(dis);
this.nom = readString(dis);
this.prenom = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.id, dos);
writeString(this.nom, dos);
writeString(this.prenom, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.id, dos);
writeString(this.nom, dos);
writeString(this.prenom, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("id=" + id);
sb.append(",nom=" + nom);
sb.append(",prenom=" + prenom);
sb.append("]");
return sb.toString();
}
public String toLogString() {
StringBuilder sb = new StringBuilder();
if (id == null) {
sb.append("<null>");
} else {
sb.append(id);
}
sb.append("|");
if (nom == null) {
sb.append("<null>");
} else {
sb.append(nom);
}
sb.append("|");
if (prenom == null) {
sb.append("<null>");
} else {
sb.append(prenom);
}
sb.append("|");
return sb.toString();
}
public int compareTo(outStruct other) {
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
final static byte[] commonByteArrayLock_FORMATION_Pos_to_csv = new byte[0];
static byte[] commonByteArray_FORMATION_Pos_to_csv = new byte[0];
public String data;
public String getData() {
return this.data;
}
public Boolean dataIsNullable() {
return true;
}
public Boolean dataIsKey() {
return false;
}
public Integer dataLength() {
return null;
}
public Integer dataPrecision() {
return null;
}
public String dataDefault() {
return null;
}
public String dataComment() {
return "";
}
public String dataPattern() {
return "";
}
public String dataOriginalDbColumnName() {
return "data";
}
private String readString(ObjectInputStream dis) throws IOException {
String strReturn = null;
int length = 0;
length = dis.readInt();
if (length == -1) {
strReturn = null;
} else {
if (length > commonByteArray_FORMATION_Pos_to_csv.length) {
if (length < 1024 && commonByteArray_FORMATION_Pos_to_csv.length == 0) {
commonByteArray_FORMATION_Pos_to_csv = new byte[1024];
} else {
commonByteArray_FORMATION_Pos_to_csv = new byte[2 * length];
}
}
dis.readFully(commonByteArray_FORMATION_Pos_to_csv, 0, length);
strReturn = new String(commonByteArray_FORMATION_Pos_to_csv, 0, length, utf8Charset);
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
if (length > commonByteArray_FORMATION_Pos_to_csv.length) {
if (length < 1024 && commonByteArray_FORMATION_Pos_to_csv.length == 0) {
commonByteArray_FORMATION_Pos_to_csv = new byte[1024];
} else {
commonByteArray_FORMATION_Pos_to_csv = new byte[2 * length];
}
}
unmarshaller.readFully(commonByteArray_FORMATION_Pos_to_csv, 0, length);
strReturn = new String(commonByteArray_FORMATION_Pos_to_csv, 0, length, utf8Charset);
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
synchronized (commonByteArrayLock_FORMATION_Pos_to_csv) {
try {
int length = 0;
this.data = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void readData(org.jboss.marshalling.Unmarshaller dis) {
synchronized (commonByteArrayLock_FORMATION_Pos_to_csv) {
try {
int length = 0;
this.data = readString(dis);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
}
public void writeData(ObjectOutputStream dos) {
try {
writeString(this.data, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public void writeData(org.jboss.marshalling.Marshaller dos) {
try {
writeString(this.data, dos);
} catch (IOException e) {
throw new RuntimeException(e);
}
}
public String toString() {
StringBuilder sb = new StringBuilder();
sb.append(super.toString());
sb.append("[");
sb.append("data=" + data);
sb.append("]");
return sb.toString();
}
public String toLogString() {
StringBuilder sb = new StringBuilder();
if (data == null) {
sb.append("<null>");
} else {
sb.append(data);
}
sb.append("|");
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
public void tFileInputPositional_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("tFileInputPositional_1_SUBPROCESS_STATE", 0);
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
outStruct out = new outStruct();
ok_Hash.put("tFileOutputDelimited_1", false);
start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
currentComponent = "tFileOutputDelimited_1";
runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "out");
int tos_count_tFileOutputDelimited_1 = 0;
if (log.isDebugEnabled())
log.debug("tFileOutputDelimited_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tFileOutputDelimited_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tFileOutputDelimited_1 = new StringBuilder();
log4jParamters_tFileOutputDelimited_1.append("Parameters:");
log4jParamters_tFileOutputDelimited_1.append("USESTREAM" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1
.append("FILENAME" + " = " + "\"D:/DFM data integration/pos_to_csv/output.csv\"");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("FIELDSEPARATOR" + " = " + "\";\"");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("APPEND" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("INCLUDEHEADER" + " = " + "true");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("COMPRESS" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("ADVANCED_SEPARATOR" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("CSV_OPTION" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("CREATE" + " = " + "true");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("SPLIT" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("FLUSHONROW" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("ROW_MODE" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("DELETE_EMPTYFILE" + " = " + "false");
log4jParamters_tFileOutputDelimited_1.append(" | ");
log4jParamters_tFileOutputDelimited_1.append("FILE_EXIST_EXCEPTION" + " = " + "true");
log4jParamters_tFileOutputDelimited_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tFileOutputDelimited_1 - " + (log4jParamters_tFileOutputDelimited_1));
}
}
new BytesLimit65535_tFileOutputDelimited_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tFileOutputDelimited_1", "tFileOutputDelimited_1", "tFileOutputDelimited");
talendJobLogProcess(globalMap);
}
String fileName_tFileOutputDelimited_1 = "";
fileName_tFileOutputDelimited_1 = (new java.io.File("D:/DFM data integration/pos_to_csv/output.csv"))
.getAbsolutePath().replace("\\", "/");
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
log.info("tFileOutputDelimited_1 - Creating directory '"
+ dir_tFileOutputDelimited_1.getCanonicalPath() + "'.");
dir_tFileOutputDelimited_1.mkdirs();
log.info("tFileOutputDelimited_1 - The directory '"
+ dir_tFileOutputDelimited_1.getCanonicalPath() + "' has been created successfully.");
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
if (filetFileOutputDelimited_1.length() == 0) {
outtFileOutputDelimited_1.write("id");
outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write("nom");
outtFileOutputDelimited_1.write(OUT_DELIM_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write("prenom");
outtFileOutputDelimited_1.write(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
outtFileOutputDelimited_1.flush();
}
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
ok_Hash.put("tMap_1", false);
start_Hash.put("tMap_1", System.currentTimeMillis());
currentComponent = "tMap_1";
runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, 0, 0, "row1");
int tos_count_tMap_1 = 0;
if (log.isDebugEnabled())
log.debug("tMap_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tMap_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tMap_1 = new StringBuilder();
log4jParamters_tMap_1.append("Parameters:");
log4jParamters_tMap_1.append("LINK_STYLE" + " = " + "AUTO");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("TEMPORARY_DATA_DIRECTORY" + " = " + "");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("ROWS_BUFFER_SIZE" + " = " + "2000000");
log4jParamters_tMap_1.append(" | ");
log4jParamters_tMap_1.append("CHANGE_HASH_AND_EQUALS_FOR_BIGDECIMAL" + " = " + "true");
log4jParamters_tMap_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tMap_1 - " + (log4jParamters_tMap_1));
}
}
new BytesLimit65535_tMap_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tMap_1", "tMap_1", "tMap");
talendJobLogProcess(globalMap);
}
int count_row1_tMap_1 = 0;
class Var__tMap_1__Struct {
}
Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
int count_out_tMap_1 = 0;
outStruct out_tmp = new outStruct();
ok_Hash.put("tFileInputPositional_1", false);
start_Hash.put("tFileInputPositional_1", System.currentTimeMillis());
currentComponent = "tFileInputPositional_1";
int tos_count_tFileInputPositional_1 = 0;
if (log.isDebugEnabled())
log.debug("tFileInputPositional_1 - " + ("Start to work."));
if (log.isDebugEnabled()) {
class BytesLimit65535_tFileInputPositional_1 {
public void limitLog4jByte() throws Exception {
StringBuilder log4jParamters_tFileInputPositional_1 = new StringBuilder();
log4jParamters_tFileInputPositional_1.append("Parameters:");
log4jParamters_tFileInputPositional_1.append("USE_EXISTING_DYNAMIC" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1
.append("FILENAME" + " = " + "\"D:/DFM data integration/pos_to_csv/input.pos\"");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("ROWSEPARATOR" + " = " + "\"\\n\"");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("ADVANCED_OPTION" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("PATTERN" + " = " + "\"5,4,5\"");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("PATTERN_UNITS" + " = " + "SYMBOLS");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("REMOVE_EMPTY_ROW" + " = " + "true");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("UNCOMPRESS" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("DIE_ON_ERROR" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("HEADER" + " = " + "0");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("FOOTER" + " = " + "0");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("LIMIT" + " = " + "");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("PROCESS_LONG_ROW" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("ADVANCED_SEPARATOR" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("TRIMALL" + " = " + "true");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("CHECK_DATE" + " = " + "false");
log4jParamters_tFileInputPositional_1.append(" | ");
log4jParamters_tFileInputPositional_1.append("ENCODING" + " = " + "\"ISO-8859-15\"");
log4jParamters_tFileInputPositional_1.append(" | ");
if (log.isDebugEnabled())
log.debug("tFileInputPositional_1 - " + (log4jParamters_tFileInputPositional_1));
}
}
new BytesLimit65535_tFileInputPositional_1().limitLog4jByte();
}
if (enableLogStash) {
talendJobLog.addCM("tFileInputPositional_1", "tFileInputPositional_1", "tFileInputPositional");
talendJobLogProcess(globalMap);
}
final StringBuffer log4jSb_tFileInputPositional_1 = new StringBuffer();
boolean useStar_tFileInputPositional_1 = false;
String pattern_tFileInputPositional_1 = "5,4,5";
final String[] positions_tFileInputPositional_1 = pattern_tFileInputPositional_1.trim().split(",");
for (int i_tFileInputPositional_1 = 0; i_tFileInputPositional_1 < positions_tFileInputPositional_1.length; i_tFileInputPositional_1++) {
if ("".equals(positions_tFileInputPositional_1[i_tFileInputPositional_1])) {
positions_tFileInputPositional_1[i_tFileInputPositional_1] = "0";
}
if ("*".equals(positions_tFileInputPositional_1[i_tFileInputPositional_1])) {
useStar_tFileInputPositional_1 = true;
}
}
if (false) {
throw new RuntimeException(
"tFileInputPositional doesn't support to work with tSetDynamicSchema when 'Guess schema from file' option is activated");
}
int nb_line_tFileInputPositional_1 = 0;
int footer_tFileInputPositional_1 = 0;
int nb_limit_tFileInputPositional_1 = -1;
class PositionalUtil_tFileInputPositional_1 {
private boolean useStar = false;
public void setUseStar(boolean useStar) {
this.useStar = useStar;
}
int parseValue_0(String row_tFileInputPositional_1, int substringBegintFileInputPositional_1,
int substringEndtFileInputPositional_1, int rowLen_tFileInputPositional_1,
String[] columnValuetFileInputPositional_1) throws java.lang.Exception {
if (positions_tFileInputPositional_1.length <= 0) {
columnValuetFileInputPositional_1[0] = "";
} else if (substringBegintFileInputPositional_1 >= rowLen_tFileInputPositional_1) {
columnValuetFileInputPositional_1[0] = "";
} else {
if ("*".equals(positions_tFileInputPositional_1[0])) {
substringEndtFileInputPositional_1 = rowLen_tFileInputPositional_1;
} else {
substringEndtFileInputPositional_1 = substringEndtFileInputPositional_1
+ Integer.parseInt(positions_tFileInputPositional_1[0]);
if (substringEndtFileInputPositional_1 > rowLen_tFileInputPositional_1) {
substringEndtFileInputPositional_1 = rowLen_tFileInputPositional_1;
}
}
columnValuetFileInputPositional_1[0] = row_tFileInputPositional_1.substring(
substringBegintFileInputPositional_1, substringEndtFileInputPositional_1);
columnValuetFileInputPositional_1[0] = columnValuetFileInputPositional_1[0].trim();
substringBegintFileInputPositional_1 = substringEndtFileInputPositional_1;
}
return substringBegintFileInputPositional_1;
}
void setValue_0(row1Struct row1, String[] columnValuetFileInputPositional_1)
throws java.lang.Exception {
row1.data = columnValuetFileInputPositional_1[0];
}
}
PositionalUtil_tFileInputPositional_1 positionalUtil_tFileInputPositional_1 = new PositionalUtil_tFileInputPositional_1();
positionalUtil_tFileInputPositional_1.setUseStar(useStar_tFileInputPositional_1);
Object filename_tFileInputPositional_1 =
"D:/DFM data integration/pos_to_csv/input.pos"
;
java.io.BufferedReader in_tFileInputPositional_1 = null;
org.talend.fileprocess.delimited.RowParser reader_tFileInputPositional_1 = null;
org.talend.fileprocess.delimited.RowParserByByte byteReader_tFileInputPositional_1 = null;
log.debug("tFileInputPositional_1 - Retrieving records from the datasource.");
String row_tFileInputPositional_1 = null;
int rowLen_tFileInputPositional_1 = 0;
String[] columnValuetFileInputPositional_1 = new String[1];
try {
if (filename_tFileInputPositional_1 instanceof java.io.InputStream) {
in_tFileInputPositional_1 = new java.io.BufferedReader(new routines.system.UnicodeReader(
(java.io.InputStream) filename_tFileInputPositional_1, "ISO-8859-15"));
resourceMap.put("in_tFileInputPositional_1", in_tFileInputPositional_1);
} else {
in_tFileInputPositional_1 = new java.io.BufferedReader(new routines.system.UnicodeReader(
new java.io.FileInputStream(String.valueOf(filename_tFileInputPositional_1)),
"ISO-8859-15"));
resourceMap.put("in_tFileInputPositional_1", in_tFileInputPositional_1);
}
reader_tFileInputPositional_1 = new org.talend.fileprocess.delimited.RowParser(
in_tFileInputPositional_1, "\n", true);
resourceMap.put("byteReader_tFileInputPositional_1", byteReader_tFileInputPositional_1);
resourceMap.put("reader_tFileInputPositional_1", reader_tFileInputPositional_1);
reader_tFileInputPositional_1.setSafetySwitch(true);
reader_tFileInputPositional_1.skipHeaders(0);
if (footer_tFileInputPositional_1 > 0) {
int available_tFileInputPositional_1 = (int) reader_tFileInputPositional_1
.getAvailableRowCount(footer_tFileInputPositional_1);
reader_tFileInputPositional_1.close();
if (filename_tFileInputPositional_1 instanceof java.io.InputStream) {
throw new java.lang.RuntimeException(
"When the input source is a stream,footer shouldn't be bigger than 0.");
}
reader_tFileInputPositional_1 = new org.talend.fileprocess.delimited.RowParser(
new java.io.BufferedReader(new routines.system.UnicodeReader(
new java.io.FileInputStream(String.valueOf(filename_tFileInputPositional_1)),
"ISO-8859-15")),
"\n", true);
resourceMap.put("byteReader_tFileInputPositional_1", byteReader_tFileInputPositional_1);
resourceMap.put("reader_tFileInputPositional_1", reader_tFileInputPositional_1);
reader_tFileInputPositional_1.skipHeaders(0);
if (nb_limit_tFileInputPositional_1 >= 0) {
nb_limit_tFileInputPositional_1 = (nb_limit_tFileInputPositional_1 > available_tFileInputPositional_1)
? available_tFileInputPositional_1
: nb_limit_tFileInputPositional_1;
} else {
nb_limit_tFileInputPositional_1 = available_tFileInputPositional_1;
}
}
} catch (java.lang.Exception e) {
globalMap.put("tFileInputPositional_1_ERROR_MESSAGE", e.getMessage());
log.error("tFileInputPositional_1 - " + e.getMessage());
System.err.println(e.getMessage());
}
while (nb_limit_tFileInputPositional_1 != 0 && reader_tFileInputPositional_1 != null
&& reader_tFileInputPositional_1.readRecord()) {
row_tFileInputPositional_1 = reader_tFileInputPositional_1.getRowRecord();
rowLen_tFileInputPositional_1 = row_tFileInputPositional_1.length();
row1 = null;
boolean whetherReject_tFileInputPositional_1 = false;
row1 = new row1Struct();
try {
int substringBegintFileInputPositional_1 = 0, substringEndtFileInputPositional_1 = 0;
int[] begin_end_tFileInputPositional_1 = new int[2];
substringBegintFileInputPositional_1 = positionalUtil_tFileInputPositional_1.parseValue_0(
row_tFileInputPositional_1, substringBegintFileInputPositional_1,
substringEndtFileInputPositional_1, rowLen_tFileInputPositional_1,
columnValuetFileInputPositional_1);
substringEndtFileInputPositional_1 = substringBegintFileInputPositional_1;
positionalUtil_tFileInputPositional_1.setValue_0(row1, columnValuetFileInputPositional_1);
log.debug("tFileInputPositional_1 - Retrieving the record "
+ (nb_line_tFileInputPositional_1 + 1) + ".");
} catch (java.lang.Exception e) {
globalMap.put("tFileInputPositional_1_ERROR_MESSAGE", e.getMessage());
whetherReject_tFileInputPositional_1 = true;
log.error("tFileInputPositional_1 - " + e.getMessage());
System.err.println(e.getMessage());
row1 = null;
}
currentComponent = "tFileInputPositional_1";
tos_count_tFileInputPositional_1++;
currentComponent = "tFileInputPositional_1";
if (row1 != null) {
currentComponent = "tMap_1";
if (runStat.update(execStat, enableLogStash, iterateId, 1, 1
, "row1", "tFileInputPositional_1", "tFileInputPositional_1", "tFileInputPositional",
"tMap_1", "tMap_1", "tMap"
)) {
talendJobLogProcess(globalMap);
}
if (log.isTraceEnabled()) {
log.trace("row1 - " + (row1 == null ? "" : row1.toLogString()));
}
boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;
boolean rejectedInnerJoin_tMap_1 = false;
boolean mainRowRejected_tMap_1 = false;
{
Var__tMap_1__Struct Var = Var__tMap_1;
out = null;
count_out_tMap_1++;
out_tmp.id = row1.data;
out_tmp.nom = row1.data;
out_tmp.prenom = row1.data;
out = out_tmp;
log.debug("tMap_1 - Outputting the record " + count_out_tMap_1
+ " of the output table 'out'.");
}
rejectedInnerJoin_tMap_1 = false;
tos_count_tMap_1++;
currentComponent = "tMap_1";
if (out != null) {
currentComponent = "tFileOutputDelimited_1";
if (runStat.update(execStat, enableLogStash, iterateId, 1, 1
, "out", "tMap_1", "tMap_1", "tMap", "tFileOutputDelimited_1",
"tFileOutputDelimited_1", "tFileOutputDelimited"
)) {
talendJobLogProcess(globalMap);
}
if (log.isTraceEnabled()) {
log.trace("out - " + (out == null ? "" : out.toLogString()));
}
StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();
if (out.id != null) {
sb_tFileOutputDelimited_1.append(out.id);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (out.nom != null) {
sb_tFileOutputDelimited_1.append(out.nom);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);
if (out.prenom != null) {
sb_tFileOutputDelimited_1.append(out.prenom);
}
sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);
nb_line_tFileOutputDelimited_1++;
resourceMap.put("nb_line_tFileOutputDelimited_1", nb_line_tFileOutputDelimited_1);
outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());
log.debug("tFileOutputDelimited_1 - Writing the record " + nb_line_tFileOutputDelimited_1
+ ".");
tos_count_tFileOutputDelimited_1++;
currentComponent = "tFileOutputDelimited_1";
currentComponent = "tFileOutputDelimited_1";
}
currentComponent = "tMap_1";
}
currentComponent = "tFileInputPositional_1";
currentComponent = "tFileInputPositional_1";
nb_line_tFileInputPositional_1++;
if (nb_limit_tFileInputPositional_1 > 0
&& nb_line_tFileInputPositional_1 >= nb_limit_tFileInputPositional_1) {
break;
}
}
log.debug("tFileInputPositional_1 - Retrieved records count: " + nb_line_tFileInputPositional_1 + " .");
if (!(filename_tFileInputPositional_1 instanceof java.io.InputStream)) {
if (reader_tFileInputPositional_1 != null) {
reader_tFileInputPositional_1.close();
}
if (byteReader_tFileInputPositional_1 != null) {
byteReader_tFileInputPositional_1.close();
}
if (in_tFileInputPositional_1 != null) {
in_tFileInputPositional_1.close();
}
}
globalMap.put("tFileInputPositional_1_NB_LINE", nb_line_tFileInputPositional_1);
resourceMap.put("finish_tFileInputPositional_1", true);
if (log.isDebugEnabled())
log.debug("tFileInputPositional_1 - " + ("Done."));
ok_Hash.put("tFileInputPositional_1", true);
end_Hash.put("tFileInputPositional_1", System.currentTimeMillis());
currentComponent = "tMap_1";
log.debug("tMap_1 - Written records count in the table 'out': " + count_out_tMap_1 + ".");
if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "row1", 2, 0,
"tFileInputPositional_1", "tFileInputPositional_1", "tFileInputPositional", "tMap_1", "tMap_1",
"tMap", "output")) {
talendJobLogProcess(globalMap);
}
if (log.isDebugEnabled())
log.debug("tMap_1 - " + ("Done."));
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
log.debug("tFileOutputDelimited_1 - Written records count: " + nb_line_tFileOutputDelimited_1 + " .");
if (runStat.updateStatAndLog(execStat, enableLogStash, resourceMap, iterateId, "out", 2, 0, "tMap_1",
"tMap_1", "tMap", "tFileOutputDelimited_1", "tFileOutputDelimited_1", "tFileOutputDelimited",
"output")) {
talendJobLogProcess(globalMap);
}
if (log.isDebugEnabled())
log.debug("tFileOutputDelimited_1 - " + ("Done."));
ok_Hash.put("tFileOutputDelimited_1", true);
end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
if (!(e instanceof TalendException)) {
log.fatal(currentComponent + " " + e.getMessage(), e);
}
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "tFileInputPositional_1";
if (resourceMap.get("finish_tFileInputPositional_1") == null) {
org.talend.fileprocess.delimited.RowParser reader_tFileInputPositional_1 = (org.talend.fileprocess.delimited.RowParser) resourceMap
.get("reader_tFileInputPositional_1");
org.talend.fileprocess.delimited.RowParserByByte byteReader_tFileInputPositional_1 = (org.talend.fileprocess.delimited.RowParserByByte) resourceMap
.get("byteReader_tFileInputPositional_1");
java.io.BufferedReader in_tFileInputPositional_1 = (java.io.BufferedReader) resourceMap
.get("in_tFileInputPositional_1");
if (reader_tFileInputPositional_1 != null) {
reader_tFileInputPositional_1.close();
}
if (byteReader_tFileInputPositional_1 != null) {
byteReader_tFileInputPositional_1.close();
}
if (in_tFileInputPositional_1 != null) {
in_tFileInputPositional_1.close();
}
}
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
globalMap.put("tFileInputPositional_1_SUBPROCESS_STATE", 1);
}
public void talendJobLogProcess(final java.util.Map<String, Object> globalMap) throws TalendException {
globalMap.put("talendJobLog_SUBPROCESS_STATE", 0);
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
ok_Hash.put("talendJobLog", false);
start_Hash.put("talendJobLog", System.currentTimeMillis());
currentComponent = "talendJobLog";
int tos_count_talendJobLog = 0;
for (JobStructureCatcherUtils.JobStructureCatcherMessage jcm : talendJobLog.getMessages()) {
org.talend.job.audit.JobContextBuilder builder_talendJobLog = org.talend.job.audit.JobContextBuilder
.create().jobName(jcm.job_name).jobId(jcm.job_id).jobVersion(jcm.job_version)
.custom("process_id", jcm.pid).custom("thread_id", jcm.tid).custom("pid", pid)
.custom("father_pid", fatherPid).custom("root_pid", rootPid);
org.talend.logging.audit.Context log_context_talendJobLog = null;
if (jcm.log_type == JobStructureCatcherUtils.LogType.PERFORMANCE) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.sourceId(jcm.sourceId)
.sourceLabel(jcm.sourceLabel).sourceConnectorType(jcm.sourceComponentName)
.targetId(jcm.targetId).targetLabel(jcm.targetLabel)
.targetConnectorType(jcm.targetComponentName).connectionName(jcm.current_connector)
.rows(jcm.row_count).duration(duration).build();
auditLogger_talendJobLog.flowExecution(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBSTART) {
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).build();
auditLogger_talendJobLog.jobstart(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.JOBEND) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment).duration(duration)
.status(jcm.status).build();
auditLogger_talendJobLog.jobstop(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.RUNCOMPONENT) {
log_context_talendJobLog = builder_talendJobLog.timestamp(jcm.moment)
.connectorType(jcm.component_name).connectorId(jcm.component_id)
.connectorLabel(jcm.component_label).build();
auditLogger_talendJobLog.runcomponent(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWINPUT) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
.rows(jcm.total_row_number).duration(duration).build();
auditLogger_talendJobLog.flowInput(log_context_talendJobLog);
} else if (jcm.log_type == JobStructureCatcherUtils.LogType.FLOWOUTPUT) {
long timeMS = jcm.end_time - jcm.start_time;
String duration = String.valueOf(timeMS);
log_context_talendJobLog = builder_talendJobLog.connectorType(jcm.component_name)
.connectorId(jcm.component_id).connectorLabel(jcm.component_label)
.connectionName(jcm.current_connector).connectionType(jcm.current_connector_type)
.rows(jcm.total_row_number).duration(duration).build();
auditLogger_talendJobLog.flowOutput(log_context_talendJobLog);
}
}
currentComponent = "talendJobLog";
tos_count_talendJobLog++;
currentComponent = "talendJobLog";
currentComponent = "talendJobLog";
currentComponent = "talendJobLog";
ok_Hash.put("talendJobLog", true);
end_Hash.put("talendJobLog", System.currentTimeMillis());
}
} catch (java.lang.Exception e) {
if (!(e instanceof TalendException)) {
log.fatal(currentComponent + " " + e.getMessage(), e);
}
TalendException te = new TalendException(e, currentComponent, globalMap);
throw te;
} catch (java.lang.Error error) {
runStat.stopThreadStat();
throw error;
} finally {
try {
currentComponent = "talendJobLog";
} catch (java.lang.Exception e) {
} catch (java.lang.Error error) {
}
resourceMap = null;
}
globalMap.put("talendJobLog_SUBPROCESS_STATE", 1);
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
final Pos_to_csv Pos_to_csvClass = new Pos_to_csv();
int exitCode = Pos_to_csvClass.runJobInTOS(args);
if (exitCode == 0) {
log.info("TalendJob: 'Pos_to_csv' - Done.");
}
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
if (!"".equals(log4jLevel)) {
if ("trace".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.TRACE);
} else if ("debug".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.DEBUG);
} else if ("info".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.INFO);
} else if ("warn".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.WARN);
} else if ("error".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.ERROR);
} else if ("fatal".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.FATAL);
} else if ("off".equalsIgnoreCase(log4jLevel)) {
org.apache.logging.log4j.core.config.Configurator.setLevel(log.getName(),
org.apache.logging.log4j.Level.OFF);
}
org.apache.logging.log4j.core.config.Configurator
.setLevel(org.apache.logging.log4j.LogManager.getRootLogger().getName(), log.getLevel());
}
log.info("TalendJob: 'Pos_to_csv' - Start.");
if (enableLogStash) {
java.util.Properties properties_talendJobLog = new java.util.Properties();
properties_talendJobLog.setProperty("root.logger", "audit");
properties_talendJobLog.setProperty("encoding", "UTF-8");
properties_talendJobLog.setProperty("application.name", "Talend Studio");
properties_talendJobLog.setProperty("service.name", "Talend Studio Job");
properties_talendJobLog.setProperty("instance.name", "Talend Studio Job Instance");
properties_talendJobLog.setProperty("propagate.appender.exceptions", "none");
properties_talendJobLog.setProperty("log.appender", "file");
properties_talendJobLog.setProperty("appender.file.path", "audit.json");
properties_talendJobLog.setProperty("appender.file.maxsize", "52428800");
properties_talendJobLog.setProperty("appender.file.maxbackup", "20");
properties_talendJobLog.setProperty("host", "false");
System.getProperties().stringPropertyNames().stream().filter(it -> it.startsWith("audit.logger."))
.forEach(key -> properties_talendJobLog.setProperty(key.substring("audit.logger.".length()),
System.getProperty(key)));
org.apache.logging.log4j.core.config.Configurator
.setLevel(properties_talendJobLog.getProperty("root.logger"), org.apache.logging.log4j.Level.DEBUG);
auditLogger_talendJobLog = org.talend.job.audit.JobEventAuditLoggerFactory
.createJobAuditLogger(properties_talendJobLog);
}
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
try {
java.util.Dictionary<String, Object> jobProperties = null;
if (inOSGi) {
jobProperties = routines.system.BundleUtils.getJobProperties(jobName);
if (jobProperties != null && jobProperties.get("context") != null) {
contextStr = (String) jobProperties.get("context");
}
}
java.io.InputStream inContext = Pos_to_csv.class.getClassLoader()
.getResourceAsStream("formation/pos_to_csv_0_1/contexts/" + contextStr + ".properties");
if (inContext == null) {
inContext = Pos_to_csv.class.getClassLoader()
.getResourceAsStream("config/contexts/" + contextStr + ".properties");
}
if (inContext != null) {
try {
if (context != null && context.isEmpty()) {
defaultProps.load(inContext);
if (inOSGi && jobProperties != null) {
java.util.Enumeration<String> keys = jobProperties.keys();
while (keys.hasMoreElements()) {
String propKey = keys.nextElement();
if (defaultProps.containsKey(propKey)) {
defaultProps.put(propKey, (String) jobProperties.get(propKey));
}
}
}
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
"", "", "", "", resumeUtil.convertToJsonText(context, ContextProperties.class, parametersToEncrypt));
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
if (enableLogStash) {
talendJobLog.addJobStartMessage();
try {
talendJobLogProcess(globalMap);
} catch (java.lang.Exception e) {
e.printStackTrace();
}
}
this.globalResumeTicket = false;
try {
errorCode = null;
tFileInputPositional_1Process(globalMap);
if (!"failure".equals(status)) {
status = "end";
}
} catch (TalendException e_tFileInputPositional_1) {
globalMap.put("tFileInputPositional_1_SUBPROCESS_STATE", -1);
e_tFileInputPositional_1.printStackTrace();
}
this.globalResumeTicket = true;
end = System.currentTimeMillis();
if (watch) {
System.out.println((end - startTime) + " milliseconds");
}
endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
if (false) {
System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Pos_to_csv");
}
if (enableLogStash) {
talendJobLog.addJobEndMessage(startTime, end, status);
try {
talendJobLogProcess(globalMap);
} catch (java.lang.Exception e) {
e.printStackTrace();
}
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
resumeUtil.flush();
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