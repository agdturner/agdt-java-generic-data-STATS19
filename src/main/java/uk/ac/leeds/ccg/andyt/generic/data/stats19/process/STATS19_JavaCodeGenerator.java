/*
 * Copyright 2018 Andy Turner.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.andyt.generic.data.stats19.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.ac.leeds.ccg.andyt.data.Data_VariableType;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_IO;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Environment;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Strings;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.io.STATS19_Files;
import uk.ac.leeds.ccg.andyt.generic.data.stats19.core.STATS19_Object;

/**
 * This class produces source code for loading STATS19 data. Source code classes
 * written in order to load the Accident data is written to
 * uk.ac.leeds.ccg.andyt.generic.data.stats19.data.accident. Source code classes
 * written in order to load the vehicle data is written to
 * uk.ac.leeds.ccg.andyt.generic.data.stats19.data.vehicle. Source code classes
 * written in order to load the casualty data is written to
 * uk.ac.leeds.ccg.andyt.generic.data.stats19.data.casualty.
 *
 * @author Andy Turner
 */
public class STATS19_JavaCodeGenerator extends STATS19_Object {

    private static final long serialVersionUID = 1L;

    // For convenience
    public STATS19_Strings Strings;
    public STATS19_Files Files;

    private String delimeter;

    protected STATS19_JavaCodeGenerator() {
        super();
        Strings = Env.Strings;
        Files = Env.Files;
    }

    public STATS19_JavaCodeGenerator(STATS19_Environment env) {
        super(env);
        Strings = Env.Strings;
        Files = Env.Files;
    }

    public static void main(String[] args) {
        STATS19_JavaCodeGenerator p;
        p = new STATS19_JavaCodeGenerator(new STATS19_Environment(
                new Generic_Environment()));
        p.delimeter = ",";
        String type;
        Object[] t;

        type = "accident";
        t = p.getFieldTypes(type);
        p.run(type, t);

        type = "casualty";
        t = p.getFieldTypes(type);
        p.run(type, t);

        type = "vehicle";
        t = p.getFieldTypes(type);
        p.run(type, t);
    }

    /**
     * Pass through the data and works out what numeric type is best to store
     * each field in the data.
     *
     * @param type
     * @return .
     */
    protected Object[] getFieldTypes(String type) {
        Object[] r;
        /**
         * The number of decimal places a value has to be correct to if it is a
         * floating point type.
         */
        int dp = 5;
        r = loadTest(type, Files.getInputDataDir(), dp);
        HashMap<String, Integer> fieldTypes;
        fieldTypes = new HashMap<>();
        String[] fields;
        /**
         * True indicates that a value of a field can be stored as a string, but
         * not a BigDecimal.
         */
        boolean[] strings;
        /**
         * True indicates that a value of a field can be stored as a BigDecimal.
         */
        boolean[] bigDecimals;
        /**
         * True indicates that a value of a field can be stored as a double.
         */
        boolean[] doubles;
        /**
         * True indicates that a value of a field can be stored as a float.
         */
        boolean[] floats;
        /**
         * True indicates that a value of a field can be stored as a BigInteger.
         */
        boolean[] bigIntegers;
        /**
         * True indicates that a value of a field can be stored as a long.
         */
        boolean[] longs;
        /**
         * True indicates that a value of a field can be stored as a int.
         */
        boolean[] ints;
        /**
         * True indicates that a value of a field can be stored as a short.
         */
        boolean[] shorts;
        /**
         * True indicates that a value of a field can be stored as a byte.
         */
        boolean[] bytes;
        fields = (String[]) r[0];
        strings = (boolean[]) r[1];
        bigDecimals = (boolean[]) r[2];
        doubles = (boolean[]) r[3];
        floats = (boolean[]) r[4];
        bigIntegers = (boolean[]) r[5];
        longs = (boolean[]) r[6];
        ints = (boolean[]) r[7];
        shorts = (boolean[]) r[8];
        bytes = (boolean[]) r[9];
        String field;
        for (int i = 0; i < strings.length; i++) {
            field = fields[i];
            if (strings[i]) {
                System.out.println("" + i + " " + "String");
                fieldTypes.put(field, 0);
            } else {
                if (bigDecimals[i]) {
                    System.out.println("" + i + " " + "BigDecimal");
                    fieldTypes.put(field, 1);
                } else {
                    if (doubles[i]) {
                        System.out.println("" + i + " " + "double");
                        fieldTypes.put(field, 2);
                    } else {
                        if (floats[i]) {
                            System.out.println("" + i + " " + "float");
                            fieldTypes.put(field, 3);
                        } else {
                            if (bigIntegers[i]) {
                                System.out.println("" + i + " " + "BigInteger");
                                fieldTypes.put(field, 4);
                            } else {
                                if (longs[i]) {
                                    System.out.println("" + i + " " + "long");
                                    fieldTypes.put(field, 5);
                                } else {
                                    if (ints[i]) {
                                        System.out.println("" + i + " " + "int");
                                        fieldTypes.put(field, 6);
                                    } else {
                                        if (shorts[i]) {
                                            System.out.println("" + i + " " + "short");
                                            fieldTypes.put(field, 7);
                                        } else {
                                            if (bytes[i]) {
                                                System.out.println("" + i + " " + "byte");
                                                fieldTypes.put(field, 8);
                                            } else {
                                                try {
                                                    throw new Exception("unrecognised type");
                                                } catch (Exception ex) {
                                                    ex.printStackTrace(System.err);
                                                    Logger.getLogger(STATS19_JavaCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return r;
    }

    /**
     *
     * @param TYPE
     * @param indir
     * @param dp
     * @return
     */
    public Object[] loadTest(String TYPE, File indir, int dp) {
        Object[] r;
        r = new Object[10];
        String[] fields;
        /**
         * True indicates that a value of a field can be stored as a string, but
         * not a BigDecimal.
         */
        boolean[] strings;
        /**
         * True indicates that a value of a field can be stored as a BigDecimal.
         */
        boolean[] bigDecimals;
        /**
         * True indicates that a value of a field can be stored as a double.
         */
        boolean[] doubles;
        /**
         * True indicates that a value of a field can be stored as a float.
         */
        boolean[] floats;
        /**
         * True indicates that a value of a field can be stored as a BigInteger.
         */
        boolean[] bigIntegers;
        /**
         * True indicates that a value of a field can be stored as a long.
         */
        boolean[] longs;
        /**
         * True indicates that a value of a field can be stored as a int.
         */
        boolean[] ints;
        /**
         * True indicates that a value of a field can be stored as a short.
         */
        boolean[] shorts;
        /**
         * True indicates that a value of a field can be stored as a byte.
         */
        boolean[] bytes;

        File f;
        f = getInputFile(TYPE, indir);
        System.out.println("<Test load " + TYPE + " STATS19 "
                + "data from " + f + ">");
        BufferedReader br;
        br = Generic_IO.getBufferedReader(f);
        String line;
        int n;
        line = br.lines().findFirst().get();
        fields = parseHeader(line);
        n = fields.length;
        strings = new boolean[n];
        bigDecimals = new boolean[n];
        doubles = new boolean[n];
        floats = new boolean[n];
        bigIntegers = new boolean[n];
        longs = new boolean[n];
        ints = new boolean[n];
        shorts = new boolean[n];
        bytes = new boolean[n];

        for (int i = 0; i < n; i++) {
            strings[i] = false;
            bigDecimals[i] = false;
            doubles[i] = false;
            floats[i] = false;
            bigIntegers[i] = false;
            longs[i] = false;
            ints[i] = false;
            shorts[i] = false;
            bytes[i] = true;
        }
        br.lines().skip(1).forEach(l -> {
            String[] split = l.split(delimeter);
            // split.length is used here instead of n as for some accident 
            // records the LSOA detail is not included!
            for (int i = 0; i < split.length; i++) {
                Data_VariableType.parse(split[i], i, dp, strings, bigDecimals,
                        doubles, floats, bigIntegers, longs, ints, shorts,
                        bytes);
            }
        });
        System.out.println("</Loading " + TYPE + " STATS19 " + "data from " + f + ">");
        r[0] = fields;
        r[1] = strings;
        r[2] = bigDecimals;
        r[3] = doubles;
        r[4] = floats;
        r[5] = bigIntegers;
        r[6] = longs;
        r[7] = ints;
        r[8] = shorts;
        r[9] = bytes;
        return r;
    }

    public File getInputFile(String type, File indir) {
        File f;
        File dir = new File(indir, "dftRoadSafetyData_Accidents_2017");
        f = new File(dir, "acc.csv");
        return f;
    }

    public void run(String type, Object[] types) {
        String[] fields;
        fields = (String[]) types[0];
        File outdir;
        outdir = new File(Files.getDataDir(), "..");
        outdir = new File(outdir, "src");
        outdir = new File(outdir, "main");
        outdir = new File(outdir, "java");
        outdir = new File(outdir, "uk");
        outdir = new File(outdir, "ac");
        outdir = new File(outdir, "leeds");
        outdir = new File(outdir, "ccg");
        outdir = new File(outdir, "andyt");
        outdir = new File(outdir, "generic");
        outdir = new File(outdir, "data");
        outdir = new File(outdir, "stats19");
        outdir = new File(outdir, "data");
        outdir = new File(outdir, type);
        outdir.mkdirs();
        String packageName;
        packageName = "uk.ac.leeds.ccg.andyt.generic.data.stats19.data.";
        packageName += type;

        File fout;
        PrintWriter pw;
        int year;
        String className;
        String extendedClassName;
        String prepend;
        prepend = "STATS19_";
        type = type.toUpperCase();
        className = prepend + "_" + type + "_Record";
        fout = new File(outdir, className + ".java");
        pw = Generic_IO.getPrintWriter(fout, false);
        writeHeaderPackageAndImports(pw, packageName, "");
        printClassDeclarationSerialVersionUID(pw, packageName,
                className, "", "");
//                // Print Field Declarations Inits And Getters
//                printFieldDeclarationsInitsAndGetters(pw, fields[w], fieldTypes);
//                // Constructor
//                pw.println("public " + className + "(String line) {");
//                pw.println("s = line.split(\"\\t\");");
//                for (int j = 0; j < headers[w].length; j++) {
//                    pw.println("init" + headers[w][j] + "(s[" + j + "]);");
//                }
        pw.println("}");
        pw.println("}");
        pw.close();

    }

    /**
     *
     * @param pw
     * @param packageName
     * @param imports
     */
    public void writeHeaderPackageAndImports(PrintWriter pw,
            String packageName, String imports) {
        pw.println("/**");
        pw.println(" * Source code generated by " + this.getClass().getName());
        pw.println(" */");
        pw.println("package " + packageName + ";");
        if (!imports.isEmpty()) {
            pw.println("import " + imports + ";");
        }
    }

    /**
     *
     * @param pw
     * @param packageName
     * @param className
     * @param implementations
     * @param extendedClassName
     */
    public void printClassDeclarationSerialVersionUID(PrintWriter pw,
            String packageName, String className, String implementations,
            String extendedClassName) {
        pw.print("public class " + className);
        if (!extendedClassName.isEmpty()) {
            pw.print(" extends " + extendedClassName + " {");
        }
        if (!implementations.isEmpty()) {
            pw.print(" implements " + implementations + " {");
        }
        pw.println();
        /**
         * This is not included for performance reasons. pw.println("private
         * static final long serialVersionUID = " + serialVersionUID + ";");
         */
    }

    /**
     * @param pw
     * @param fields
     * @param fieldTypes
     * @param v0
     */
    public void printFieldDeclarationsInitsAndGetters(PrintWriter pw,
            TreeSet<String> fields, HashMap<String, Integer> fieldTypes,
            HashMap<String, Byte> v0) {
        // Field declarations
        printFieldDeclarations(pw, fields, fieldTypes);
        // Field init
        printFieldInits(pw, fields, fieldTypes, v0);
        // Field getters
        printFieldGetters(pw, fields, fieldTypes);
    }

    /**
     * @param pw
     * @param fields
     * @param fieldTypes
     */
    public void printFieldDeclarations(PrintWriter pw, TreeSet<String> fields,
            HashMap<String, Integer> fieldTypes) {
        String field;
        int fieldType;
        Iterator<String> ite;
        ite = fields.iterator();
        while (ite.hasNext()) {
            field = ite.next();
            fieldType = fieldTypes.get(field);
            switch (fieldType) {
                case 0:
                    pw.println("protected String " + field + ";");
                    break;
                case 1:
                    pw.println("protected double " + field + ";");
                    break;
                case 2:
                    pw.println("protected int " + field + ";");
                    break;
                case 3:
                    pw.println("protected short " + field + ";");
                    break;
                case 4:
                    pw.println("protected byte " + field + ";");
                    break;
                default:
                    pw.println("protected boolean " + field + ";");
                    break;
            }
        }
    }

    /**
     *
     * @param pw
     * @param fields
     * @param fieldTypes
     */
    public void printFieldGetters(PrintWriter pw, TreeSet<String> fields,
            HashMap<String, Integer> fieldTypes) {
        String field;
        int fieldType;
        Iterator<String> ite;
        ite = fields.iterator();
        while (ite.hasNext()) {
            field = ite.next();
            fieldType = fieldTypes.get(field);
            switch (fieldType) {
                case 0:
                    pw.println("public String get" + field + "() {");
                    break;
                case 1:
                    pw.println("public double get" + field + "() {");
                    break;
                case 2:
                    pw.println("public int get" + field + "() {");
                    break;
                case 3:
                    pw.println("public short get" + field + "() {");
                    break;
                case 4:
                    pw.println("public byte get" + field + "() {");
                    break;
                default:
                    pw.println("public boolean get" + field + "() {");
                    break;
            }
            pw.println("return " + field + ";");
            pw.println("}");
            pw.println();
        }
    }

    /**
     *
     * @param pw
     * @param fields
     * @param fieldTypes
     * @param v0
     */
    public void printFieldInits(PrintWriter pw, TreeSet<String> fields,
            HashMap<String, Integer> fieldTypes, HashMap<String, Byte> v0) {
        String field;
        int fieldType;
        Iterator<String> ite;
        ite = fields.iterator();
        while (ite.hasNext()) {
            field = ite.next();
            fieldType = fieldTypes.get(field);
            switch (fieldType) {
                case 0:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println(field + " = s;");
                    break;
                case 1:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println(field + " = Double.parseDouble(s);");
                    pw.println("} else {");
                    pw.println(field + " = Double.NaN;");
                    break;
                case 2:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println(field + " = Integer.parseInt(s);");
                    pw.println("} else {");
                    pw.println(field + " = Integer.MIN_VALUE;");
                    break;
                case 3:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println(field + " = Short.parseShort(s);");
                    pw.println("} else {");
                    pw.println(field + " = Short.MIN_VALUE;");
                    break;
                case 4:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println(field + " = Byte.parseByte(s);");
                    pw.println("} else {");
                    pw.println(field + " = Byte.MIN_VALUE;");
                    break;
                default:
                    pw.println("protected final void init" + field + "(String s) {");
                    pw.println("if (!s.trim().isEmpty()) {");
                    pw.println("byte b = Byte.parseByte(s);");
                    if (v0.get(field) == null) {
                        pw.println(field + " = false;");
                    } else {
                        pw.println("if (b == " + v0.get(field) + ") {");
                        pw.println(field + " = false;");
                        pw.println("} else {");
                        pw.println(field + " = true;");
                        pw.println("}");
                    }
                    break;
            }
            pw.println("}");
            pw.println("}");
            pw.println();
        }
    }

    /**
     * Thinking to returns a lists of IDs...
     *
     * @param header
     * @return
     */
    public String[] parseHeader(String header) {
        String[] r;
        String h1;
        h1 = header.toUpperCase();
        r = h1.split(delimeter);
        return r;
    }

    protected HashMap<String, Byte> setCommonBooleanMaps(
            HashMap<String, Byte>[] v0ms,
            HashMap<String, Byte>[] v1ms,
            TreeSet<String>[] allFields,
            HashMap<String, Integer> fieldTypes) {
        HashMap<String, Byte> v0m;
        HashMap<String, Byte> v1m;
        Iterator<String> ites0;
        Iterator<String> ites1;
        String field0;
        String field1;
        TreeSet<String> fields;
        fields = allFields[5];
        HashMap<String, Byte> v0m1;
        HashMap<String, Byte> v1m1;
        v0m1 = new HashMap<>();
        v1m1 = new HashMap<>();
        ites0 = fields.iterator();
        byte v0;
        Byte v1;
        Byte v01;
        Byte v11;
        while (ites0.hasNext()) {
            field0 = ites0.next();
            if (fieldTypes.get(field0) == 5) {
                for (int w = 0; w < v0ms.length; w++) {
                    v0m = v0ms[w];
                    v1m = v1ms[w];
                    ites1 = v0m.keySet().iterator();
                    while (ites1.hasNext()) {
                        field1 = ites1.next();
                        if (field0.equalsIgnoreCase(field1)) {
                            v0 = v0m.get(field1);
                            if (v1m == null) {
                                v1 = Byte.MIN_VALUE;
                            } else {
                                //System.out.println("field1 " + field1);
                                //System.out.println("field1 " + field1);
                                v1 = v1m.get(field1);
                                if (v1 == null) {
                                    v1 = Byte.MIN_VALUE;
                                }
                            }
                            v01 = v0m1.get(field1);
                            v11 = v1m1.get(field1);
                            if (v01 == null) {
                                v0m1.put(field1, v0);
                            } else {
                                if (v01 != v0) {
                                    // Field better stored as a byte than boolean.
                                    fieldTypes.put(field1, 4);
                                }
                                if (v11 == null) {
                                    v1m1.put(field1, v1);
                                } else {
                                    if (v1 != v11.byteValue()) {
                                        // Field better stored as a byte than boolean.
                                        fieldTypes.put(field1, 4);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return v0m1;
    }

    /**
     * Finds and returns those fields that are in common and those fields .
     * result[0] are the fields in common with all.
     *
     * @param headers
     * @return
     */
    public ArrayList<String>[] getFieldsList(ArrayList<String> headers) {
        ArrayList<String>[] r;
        int size;
        size = headers.size();
        r = new ArrayList[size];
        Iterator<String> ite;
        ite = headers.iterator();
        int i;
        i = 0;
        while (ite.hasNext()) {
            r[i] = getFieldsList(ite.next());
            i++;
        }
        return r;
    }

    /**
     *
     * @param fields
     * @return
     */
    public TreeSet<String> getFields(String[] fields) {
        TreeSet<String> r;
        r = new TreeSet<>();
        r.addAll(Arrays.asList(fields));
        return r;
    }

    /**
     *
     * @param s
     * @return
     */
    public ArrayList<String> getFieldsList(String s) {
        ArrayList<String> r;
        r = new ArrayList<>();
        String[] split;
        split = s.split("\t");
        r.addAll(Arrays.asList(split));
        return r;
    }

    /**
     * Returns all the values common to s1, s2, s3, s4 and s5 and removes all
     * these common fields from s1, s2, s3, s4 and s5.
     *
     * @param s1
     * @param s2
     * @param s3 May be null.
     * @param s4 May be null.
     * @param s5 May be null.
     * @return
     * @Todo generalise
     */
    public TreeSet<String> getFieldsInCommon(TreeSet<String> s1,
            TreeSet<String> s2, TreeSet<String> s3, TreeSet<String> s4,
            TreeSet<String> s5) {
        TreeSet<String> result;
        result = new TreeSet<>();
        result.addAll(s1);
        result.retainAll(s2);
        if (s3 != null) {
            result.retainAll(s3);
        }
        if (s4 != null) {
            result.retainAll(s4);
        }
        if (s5 != null) {
            result.retainAll(s5);
        }
        return result;
    }
}
