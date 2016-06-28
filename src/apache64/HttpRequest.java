/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apache64;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author 10070124
 */
final class HttpRequest implements Runnable {

    final static String CRLF = "\r\n";
    Socket socket;

    // Constructor
    public HttpRequest(Socket socket) throws Exception {
        this.socket = socket;
    }

    // Implement the run() method of the Runnable interface.
    @Override
    public void run() {
        try {
            processRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void processRequest() throws Exception {
        // Get a reference to the socket's input and output streams.
        InputStream is = socket.getInputStream();
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());

        // Set up input stream filters.
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Get the request line of the HTTP request message.
        String requestLine = br.readLine();

        // Extract the filename from the request line.
        StringTokenizer tokens = new StringTokenizer(requestLine);
        tokens.nextToken(); // skip over the method, which should be "GET"
        String fileName = tokens.nextToken();

        // Prepend a "." so that file request is within the current directory.
        fileName = "./www" + fileName;

        //System.out.println("File" + fileName);
        //System.out.println(".  -> " + new File(".").getCanonicalPath());
        //System.out.println("Diretorio: " + new File(fileName).getCanonicalPath());
        // Open the requested file.
        FileInputStream fis = null;
        boolean fileExists = true;
        try {
            fis = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            fileExists = false;
        }

        // Debug info for private use
        System.out.println("Incoming!!!");
        System.out.println(requestLine);
        String headerLine = null;
        while ((headerLine = br.readLine()).length() != 0) {
            System.out.println(headerLine);
        }

        // Construct the response message.
        String statusLine = null;
        String contentTypeLine = null;
        String entityBody = null;
        if (fileExists) {
            statusLine = "HTTP/1.0 200 OK" + CRLF;
            contentTypeLine = "Content­Type: "
                    + contentType(fileName) + CRLF;
        } else {
            statusLine = "HTTP/1.0 404 Not Found" + CRLF;
            contentTypeLine = "Content­Type: text/html" + CRLF;
            entityBody = "<HTML>"
                    + "<HEAD><TITLE>Not Found</TITLE></HEAD>"
                    + "<BODY>Not Found<br>Trabalho de Redes...</BODY></HTML>";
        }

        // Send the status line.
        os.writeBytes(statusLine);

        // Send the content type line.
        os.writeBytes(contentTypeLine);

        // Send a blank line to indicate the end of the header lines.
        os.writeBytes(CRLF);

        // Send the entity body.
        if (fileExists) {
            sendBytes(fis, os);
            fis.close();
        } else {
            os.writeBytes(entityBody);
        }

        // Close streams and socket.
        os.close();
        br.close();
        socket.close();
    }

    private static void sendBytes(FileInputStream fis,
            OutputStream os) throws Exception {
        // Construct a 1K buffer to hold bytes on their way to the socket.
        byte[] buffer = new byte[1024];
        int bytes = 0;

        // Copy requested file into the socket's output stream.
        while ((bytes = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytes);
        }
    }

    private static String contentType(String fileName) {
        if (fileName.endsWith(".123")) {
            return "application/vnd.lotus-1-2-3";
        }
        if (fileName.endsWith(".3dml")) {
            return "text/vnd.in3d.3dml";
        }
        if (fileName.endsWith(".3ds")) {
            return "image/x-3ds";
        }
        if (fileName.endsWith(".3g2")) {
            return "video/3gpp2";
        }
        if (fileName.endsWith(".3gp")) {
            return "video/3gpp";
        }
        if (fileName.endsWith(".7z")) {
            return "application/x-7z-compressed";
        }
        if (fileName.endsWith(".aab")) {
            return "application/x-authorware-bin";
        }
        if (fileName.endsWith(".aac")) {
            return "audio/x-aac";
        }
        if (fileName.endsWith(".aam")) {
            return "application/x-authorware-map";
        }
        if (fileName.endsWith(".aas")) {
            return "application/x-authorware-seg";
        }
        if (fileName.endsWith(".abs")) {
            return "audio/x-mpeg";
        }
        if (fileName.endsWith(".abw")) {
            return "application/x-abiword";
        }
        if (fileName.endsWith(".ac")) {
            return "application/pkix-attr-cert";
        }
        if (fileName.endsWith(".acc")) {
            return "application/vnd.americandynamics.acc";
        }
        if (fileName.endsWith(".ace")) {
            return "application/x-ace-compressed";
        }
        if (fileName.endsWith(".acu")) {
            return "application/vnd.acucobol";
        }
        if (fileName.endsWith(".acutc")) {
            return "application/vnd.acucorp";
        }
        if (fileName.endsWith(".adp")) {
            return "audio/adpcm";
        }
        if (fileName.endsWith(".aep")) {
            return "application/vnd.audiograph";
        }
        if (fileName.endsWith(".afm")) {
            return "application/x-font-type1";
        }
        if (fileName.endsWith(".afp")) {
            return "application/vnd.ibm.modcap";
        }
        if (fileName.endsWith(".ahead")) {
            return "application/vnd.ahead.space";
        }
        if (fileName.endsWith(".ai")) {
            return "application/postscript";
        }
        if (fileName.endsWith(".aif")) {
            return "audio/x-aiff";
        }
        if (fileName.endsWith(".aifc")) {
            return "audio/x-aiff";
        }
        if (fileName.endsWith(".aiff")) {
            return "audio/x-aiff";
        }
        if (fileName.endsWith(".aim")) {
            return "application/x-aim";
        }
        if (fileName.endsWith(".air")) {
            return "application/vnd.adobe.air-application-installer-package+zip";
        }
        if (fileName.endsWith(".ait")) {
            return "application/vnd.dvb.ait";
        }
        if (fileName.endsWith(".ami")) {
            return "application/vnd.amiga.ami";
        }
        if (fileName.endsWith(".anx")) {
            return "application/annodex";
        }
        if (fileName.endsWith(".apk")) {
            return "application/vnd.android.package-archive";
        }
        if (fileName.endsWith(".appcache")) {
            return "text/cache-manifest";
        }
        if (fileName.endsWith(".application")) {
            return "application/x-ms-application";
        }
        if (fileName.endsWith(".apr")) {
            return "application/vnd.lotus-approach";
        }
        if (fileName.endsWith(".arc")) {
            return "application/x-freearc";
        }
        if (fileName.endsWith(".art")) {
            return "image/x-jg";
        }
        if (fileName.endsWith(".asc")) {
            return "application/pgp-signature";
        }
        if (fileName.endsWith(".asf")) {
            return "video/x-ms-asf";
        }
        if (fileName.endsWith(".asm")) {
            return "text/x-asm";
        }
        if (fileName.endsWith(".aso")) {
            return "application/vnd.accpac.simply.aso";
        }
        if (fileName.endsWith(".asx")) {
            return "video/x-ms-asf";
        }
        if (fileName.endsWith(".atc")) {
            return "application/vnd.acucorp";
        }
        if (fileName.endsWith(".atom")) {
            return "application/atom+xml";
        }
        if (fileName.endsWith(".atomcat")) {
            return "application/atomcat+xml";
        }
        if (fileName.endsWith(".atomsvc")) {
            return "application/atomsvc+xml";
        }
        if (fileName.endsWith(".atx")) {
            return "application/vnd.antix.game-component";
        }
        if (fileName.endsWith(".au")) {
            return "audio/basic";
        }
        if (fileName.endsWith(".avi")) {
            return "video/x-msvideo";
        }
        if (fileName.endsWith(".avx")) {
            return "video/x-rad-screenplay";
        }
        if (fileName.endsWith(".aw")) {
            return "application/applixware";
        }
        if (fileName.endsWith(".axa")) {
            return "audio/annodex";
        }
        if (fileName.endsWith(".axv")) {
            return "video/annodex";
        }
        if (fileName.endsWith(".azf")) {
            return "application/vnd.airzip.filesecure.azf";
        }
        if (fileName.endsWith(".azs")) {
            return "application/vnd.airzip.filesecure.azs";
        }
        if (fileName.endsWith(".azw")) {
            return "application/vnd.amazon.ebook";
        }
        if (fileName.endsWith(".bat")) {
            return "application/x-msdownload";
        }
        if (fileName.endsWith(".bcpio")) {
            return "application/x-bcpio";
        }
        if (fileName.endsWith(".bdf")) {
            return "application/x-font-bdf";
        }
        if (fileName.endsWith(".bdm")) {
            return "application/vnd.syncml.dm+wbxml";
        }
        if (fileName.endsWith(".bed")) {
            return "application/vnd.realvnc.bed";
        }
        if (fileName.endsWith(".bh2")) {
            return "application/vnd.fujitsu.oasysprs";
        }
        if (fileName.endsWith(".bin")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".blb")) {
            return "application/x-blorb";
        }
        if (fileName.endsWith(".blorb")) {
            return "application/x-blorb";
        }
        if (fileName.endsWith(".bmi")) {
            return "application/vnd.bmi";
        }
        if (fileName.endsWith(".bmp")) {
            return "image/bmp";
        }
        if (fileName.endsWith(".body")) {
            return "text/html";
        }
        if (fileName.endsWith(".book")) {
            return "application/vnd.framemaker";
        }
        if (fileName.endsWith(".box")) {
            return "application/vnd.previewsystems.box";
        }
        if (fileName.endsWith(".boz")) {
            return "application/x-bzip2";
        }
        if (fileName.endsWith(".bpk")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".btif")) {
            return "image/prs.btif";
        }
        if (fileName.endsWith(".bz")) {
            return "application/x-bzip";
        }
        if (fileName.endsWith(".bz2")) {
            return "application/x-bzip2";
        }
        if (fileName.endsWith(".c")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".c11amc")) {
            return "application/vnd.cluetrust.cartomobile-config";
        }
        if (fileName.endsWith(".c11amz")) {
            return "application/vnd.cluetrust.cartomobile-config-pkg";
        }
        if (fileName.endsWith(".c4d")) {
            return "application/vnd.clonk.c4group";
        }
        if (fileName.endsWith(".c4f")) {
            return "application/vnd.clonk.c4group";
        }
        if (fileName.endsWith(".c4g")) {
            return "application/vnd.clonk.c4group";
        }
        if (fileName.endsWith(".c4p")) {
            return "application/vnd.clonk.c4group";
        }
        if (fileName.endsWith(".c4u")) {
            return "application/vnd.clonk.c4group";
        }
        if (fileName.endsWith(".cab")) {
            return "application/vnd.ms-cab-compressed";
        }
        if (fileName.endsWith(".caf")) {
            return "audio/x-caf";
        }
        if (fileName.endsWith(".cap")) {
            return "application/vnd.tcpdump.pcap";
        }
        if (fileName.endsWith(".car")) {
            return "application/vnd.curl.car";
        }
        if (fileName.endsWith(".cat")) {
            return "application/vnd.ms-pki.seccat";
        }
        if (fileName.endsWith(".cb7")) {
            return "application/x-cbr";
        }
        if (fileName.endsWith(".cba")) {
            return "application/x-cbr";
        }
        if (fileName.endsWith(".cbr")) {
            return "application/x-cbr";
        }
        if (fileName.endsWith(".cbt")) {
            return "application/x-cbr";
        }
        if (fileName.endsWith(".cbz")) {
            return "application/x-cbr";
        }
        if (fileName.endsWith(".cc")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".cct")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".ccxml")) {
            return "application/ccxml+xml";
        }
        if (fileName.endsWith(".cdbcmsg")) {
            return "application/vnd.contact.cmsg";
        }
        if (fileName.endsWith(".cdf")) {
            return "application/x-cdf";
        }
        if (fileName.endsWith(".cdkey")) {
            return "application/vnd.mediastation.cdkey";
        }
        if (fileName.endsWith(".cdmia")) {
            return "application/cdmi-capability";
        }
        if (fileName.endsWith(".cdmic")) {
            return "application/cdmi-container";
        }
        if (fileName.endsWith(".cdmid")) {
            return "application/cdmi-domain";
        }
        if (fileName.endsWith(".cdmio")) {
            return "application/cdmi-object";
        }
        if (fileName.endsWith(".cdmiq")) {
            return "application/cdmi-queue";
        }
        if (fileName.endsWith(".cdx")) {
            return "chemical/x-cdx";
        }
        if (fileName.endsWith(".cdxml")) {
            return "application/vnd.chemdraw+xml";
        }
        if (fileName.endsWith(".cdy")) {
            return "application/vnd.cinderella";
        }
        if (fileName.endsWith(".cer")) {
            return "application/pkix-cert";
        }
        if (fileName.endsWith(".cfs")) {
            return "application/x-cfs-compressed";
        }
        if (fileName.endsWith(".cgm")) {
            return "image/cgm";
        }
        if (fileName.endsWith(".chat")) {
            return "application/x-chat";
        }
        if (fileName.endsWith(".chm")) {
            return "application/vnd.ms-htmlhelp";
        }
        if (fileName.endsWith(".chrt")) {
            return "application/vnd.kde.kchart";
        }
        if (fileName.endsWith(".cif")) {
            return "chemical/x-cif";
        }
        if (fileName.endsWith(".cii")) {
            return "application/vnd.anser-web-certificate-issue-initiation";
        }
        if (fileName.endsWith(".cil")) {
            return "application/vnd.ms-artgalry";
        }
        if (fileName.endsWith(".cla")) {
            return "application/vnd.claymore";
        }
        if (fileName.endsWith(".class")) {
            return "application/java";
        }
        if (fileName.endsWith(".clkk")) {
            return "application/vnd.crick.clicker.keyboard";
        }
        if (fileName.endsWith(".clkp")) {
            return "application/vnd.crick.clicker.palette";
        }
        if (fileName.endsWith(".clkt")) {
            return "application/vnd.crick.clicker.template";
        }
        if (fileName.endsWith(".clkw")) {
            return "application/vnd.crick.clicker.wordbank";
        }
        if (fileName.endsWith(".clkx")) {
            return "application/vnd.crick.clicker";
        }
        if (fileName.endsWith(".clp")) {
            return "application/x-msclip";
        }
        if (fileName.endsWith(".cmc")) {
            return "application/vnd.cosmocaller";
        }
        if (fileName.endsWith(".cmdf")) {
            return "chemical/x-cmdf";
        }
        if (fileName.endsWith(".cml")) {
            return "chemical/x-cml";
        }
        if (fileName.endsWith(".cmp")) {
            return "application/vnd.yellowriver-custom-menu";
        }
        if (fileName.endsWith(".cmx")) {
            return "image/x-cmx";
        }
        if (fileName.endsWith(".cod")) {
            return "application/vnd.rim.cod";
        }
        if (fileName.endsWith(".com")) {
            return "application/x-msdownload";
        }
        if (fileName.endsWith(".conf")) {
            return "text/plain";
        }
        if (fileName.endsWith(".cpio")) {
            return "application/x-cpio";
        }
        if (fileName.endsWith(".cpp")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".cpt")) {
            return "application/mac-compactpro";
        }
        if (fileName.endsWith(".crd")) {
            return "application/x-mscardfile";
        }
        if (fileName.endsWith(".crl")) {
            return "application/pkix-crl";
        }
        if (fileName.endsWith(".crt")) {
            return "application/x-x509-ca-cert";
        }
        if (fileName.endsWith(".cryptonote")) {
            return "application/vnd.rig.cryptonote";
        }
        if (fileName.endsWith(".csh")) {
            return "application/x-csh";
        }
        if (fileName.endsWith(".csml")) {
            return "chemical/x-csml";
        }
        if (fileName.endsWith(".csp")) {
            return "application/vnd.commonspace";
        }
        if (fileName.endsWith(".css")) {
            return "text/css";
        }
        if (fileName.endsWith(".cst")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".csv")) {
            return "text/csv";
        }
        if (fileName.endsWith(".cu")) {
            return "application/cu-seeme";
        }
        if (fileName.endsWith(".curl")) {
            return "text/vnd.curl";
        }
        if (fileName.endsWith(".cww")) {
            return "application/prs.cww";
        }
        if (fileName.endsWith(".cxt")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".cxx")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".dae")) {
            return "model/vnd.collada+xml";
        }
        if (fileName.endsWith(".daf")) {
            return "application/vnd.mobius.daf";
        }
        if (fileName.endsWith(".dart")) {
            return "application/vnd.dart";
        }
        if (fileName.endsWith(".dataless")) {
            return "application/vnd.fdsn.seed";
        }
        if (fileName.endsWith(".davmount")) {
            return "application/davmount+xml";
        }
        if (fileName.endsWith(".dbk")) {
            return "application/docbook+xml";
        }
        if (fileName.endsWith(".dcr")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".dcurl")) {
            return "text/vnd.curl.dcurl";
        }
        if (fileName.endsWith(".dd2")) {
            return "application/vnd.oma.dd2+xml";
        }
        if (fileName.endsWith(".ddd")) {
            return "application/vnd.fujixerox.ddd";
        }
        if (fileName.endsWith(".deb")) {
            return "application/x-debian-package";
        }
        if (fileName.endsWith(".def")) {
            return "text/plain";
        }
        if (fileName.endsWith(".deploy")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".der")) {
            return "application/x-x509-ca-cert";
        }
        if (fileName.endsWith(".dfac")) {
            return "application/vnd.dreamfactory";
        }
        if (fileName.endsWith(".dgc")) {
            return "application/x-dgc-compressed";
        }
        if (fileName.endsWith(".dib")) {
            return "image/bmp";
        }
        if (fileName.endsWith(".dic")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".dir")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".dis")) {
            return "application/vnd.mobius.dis";
        }
        if (fileName.endsWith(".dist")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".distz")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".djv")) {
            return "image/vnd.djvu";
        }
        if (fileName.endsWith(".djvu")) {
            return "image/vnd.djvu";
        }
        if (fileName.endsWith(".dll")) {
            return "application/x-msdownload";
        }
        if (fileName.endsWith(".dmg")) {
            return "application/x-apple-diskimage";
        }
        if (fileName.endsWith(".dmp")) {
            return "application/vnd.tcpdump.pcap";
        }
        if (fileName.endsWith(".dms")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".dna")) {
            return "application/vnd.dna";
        }
        if (fileName.endsWith(".doc")) {
            return "application/msword";
        }
        if (fileName.endsWith(".docm")) {
            return "application/vnd.ms-word.document.macroenabled.12";
        }
        if (fileName.endsWith(".docx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        }
        if (fileName.endsWith(".dot")) {
            return "application/msword";
        }
        if (fileName.endsWith(".dotm")) {
            return "application/vnd.ms-word.template.macroenabled.12";
        }
        if (fileName.endsWith(".dotx")) {
            return "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
        }
        if (fileName.endsWith(".dp")) {
            return "application/vnd.osgi.dp";
        }
        if (fileName.endsWith(".dpg")) {
            return "application/vnd.dpgraph";
        }
        if (fileName.endsWith(".dra")) {
            return "audio/vnd.dra";
        }
        if (fileName.endsWith(".dsc")) {
            return "text/prs.lines.tag";
        }
        if (fileName.endsWith(".dssc")) {
            return "application/dssc+der";
        }
        if (fileName.endsWith(".dtb")) {
            return "application/x-dtbook+xml";
        }
        if (fileName.endsWith(".dtd")) {
            return "application/xml-dtd";
        }
        if (fileName.endsWith(".dts")) {
            return "audio/vnd.dts";
        }
        if (fileName.endsWith(".dtshd")) {
            return "audio/vnd.dts.hd";
        }
        if (fileName.endsWith(".dump")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".dv")) {
            return "video/x-dv";
        }
        if (fileName.endsWith(".dvb")) {
            return "video/vnd.dvb.file";
        }
        if (fileName.endsWith(".dvi")) {
            return "application/x-dvi";
        }
        if (fileName.endsWith(".dwf")) {
            return "model/vnd.dwf";
        }
        if (fileName.endsWith(".dwg")) {
            return "image/vnd.dwg";
        }
        if (fileName.endsWith(".dxf")) {
            return "image/vnd.dxf";
        }
        if (fileName.endsWith(".dxp")) {
            return "application/vnd.spotfire.dxp";
        }
        if (fileName.endsWith(".dxr")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".ecelp4800")) {
            return "audio/vnd.nuera.ecelp4800";
        }
        if (fileName.endsWith(".ecelp7470")) {
            return "audio/vnd.nuera.ecelp7470";
        }
        if (fileName.endsWith(".ecelp9600")) {
            return "audio/vnd.nuera.ecelp9600";
        }
        if (fileName.endsWith(".ecma")) {
            return "application/ecmascript";
        }
        if (fileName.endsWith(".edm")) {
            return "application/vnd.novadigm.edm";
        }
        if (fileName.endsWith(".edx")) {
            return "application/vnd.novadigm.edx";
        }
        if (fileName.endsWith(".efif")) {
            return "application/vnd.picsel";
        }
        if (fileName.endsWith(".ei6")) {
            return "application/vnd.pg.osasli";
        }
        if (fileName.endsWith(".elc")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".emf")) {
            return "application/x-msmetafile";
        }
        if (fileName.endsWith(".eml")) {
            return "message/rfc822";
        }
        if (fileName.endsWith(".emma")) {
            return "application/emma+xml";
        }
        if (fileName.endsWith(".emz")) {
            return "application/x-msmetafile";
        }
        if (fileName.endsWith(".eol")) {
            return "audio/vnd.digital-winds";
        }
        if (fileName.endsWith(".eot")) {
            return "application/vnd.ms-fontobject";
        }
        if (fileName.endsWith(".eps")) {
            return "application/postscript";
        }
        if (fileName.endsWith(".epub")) {
            return "application/epub+zip";
        }
        if (fileName.endsWith(".es3")) {
            return "application/vnd.eszigno3+xml";
        }
        if (fileName.endsWith(".esa")) {
            return "application/vnd.osgi.subsystem";
        }
        if (fileName.endsWith(".esf")) {
            return "application/vnd.epson.esf";
        }
        if (fileName.endsWith(".et3")) {
            return "application/vnd.eszigno3+xml";
        }
        if (fileName.endsWith(".etx")) {
            return "text/x-setext";
        }
        if (fileName.endsWith(".eva")) {
            return "application/x-eva";
        }
        if (fileName.endsWith(".evy")) {
            return "application/x-envoy";
        }
        if (fileName.endsWith(".exe")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".exi")) {
            return "application/exi";
        }
        if (fileName.endsWith(".ext")) {
            return "application/vnd.novadigm.ext";
        }
        if (fileName.endsWith(".ez")) {
            return "application/andrew-inset";
        }
        if (fileName.endsWith(".ez2")) {
            return "application/vnd.ezpix-album";
        }
        if (fileName.endsWith(".ez3")) {
            return "application/vnd.ezpix-package";
        }
        if (fileName.endsWith(".f")) {
            return "text/x-fortran";
        }
        if (fileName.endsWith(".f4v")) {
            return "video/x-f4v";
        }
        if (fileName.endsWith(".f77")) {
            return "text/x-fortran";
        }
        if (fileName.endsWith(".f90")) {
            return "text/x-fortran";
        }
        if (fileName.endsWith(".fbs")) {
            return "image/vnd.fastbidsheet";
        }
        if (fileName.endsWith(".fcdt")) {
            return "application/vnd.adobe.formscentral.fcdt";
        }
        if (fileName.endsWith(".fcs")) {
            return "application/vnd.isac.fcs";
        }
        if (fileName.endsWith(".fdf")) {
            return "application/vnd.fdf";
        }
        if (fileName.endsWith(".fe_launch")) {
            return "application/vnd.denovo.fcselayout-link";
        }
        if (fileName.endsWith(".fg5")) {
            return "application/vnd.fujitsu.oasysgp";
        }
        if (fileName.endsWith(".fgd")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".fh")) {
            return "image/x-freehand";
        }
        if (fileName.endsWith(".fh4")) {
            return "image/x-freehand";
        }
        if (fileName.endsWith(".fh5")) {
            return "image/x-freehand";
        }
        if (fileName.endsWith(".fh7")) {
            return "image/x-freehand";
        }
        if (fileName.endsWith(".fhc")) {
            return "image/x-freehand";
        }
        if (fileName.endsWith(".fig")) {
            return "application/x-xfig";
        }
        if (fileName.endsWith(".flac")) {
            return "audio/flac";
        }
        if (fileName.endsWith(".fli")) {
            return "video/x-fli";
        }
        if (fileName.endsWith(".flo")) {
            return "application/vnd.micrografx.flo";
        }
        if (fileName.endsWith(".flv")) {
            return "video/x-flv";
        }
        if (fileName.endsWith(".flw")) {
            return "application/vnd.kde.kivio";
        }
        if (fileName.endsWith(".flx")) {
            return "text/vnd.fmi.flexstor";
        }
        if (fileName.endsWith(".fly")) {
            return "text/vnd.fly";
        }
        if (fileName.endsWith(".fm")) {
            return "application/vnd.framemaker";
        }
        if (fileName.endsWith(".fnc")) {
            return "application/vnd.frogans.fnc";
        }
        if (fileName.endsWith(".for")) {
            return "text/x-fortran";
        }
        if (fileName.endsWith(".fpx")) {
            return "image/vnd.fpx";
        }
        if (fileName.endsWith(".frame")) {
            return "application/vnd.framemaker";
        }
        if (fileName.endsWith(".fsc")) {
            return "application/vnd.fsc.weblaunch";
        }
        if (fileName.endsWith(".fst")) {
            return "image/vnd.fst";
        }
        if (fileName.endsWith(".ftc")) {
            return "application/vnd.fluxtime.clip";
        }
        if (fileName.endsWith(".fti")) {
            return "application/vnd.anser-web-funds-transfer-initiation";
        }
        if (fileName.endsWith(".fvt")) {
            return "video/vnd.fvt";
        }
        if (fileName.endsWith(".fxp")) {
            return "application/vnd.adobe.fxp";
        }
        if (fileName.endsWith(".fxpl")) {
            return "application/vnd.adobe.fxp";
        }
        if (fileName.endsWith(".fzs")) {
            return "application/vnd.fuzzysheet";
        }
        if (fileName.endsWith(".g2w")) {
            return "application/vnd.geoplan";
        }
        if (fileName.endsWith(".g3")) {
            return "image/g3fax";
        }
        if (fileName.endsWith(".g3w")) {
            return "application/vnd.geospace";
        }
        if (fileName.endsWith(".gac")) {
            return "application/vnd.groove-account";
        }
        if (fileName.endsWith(".gam")) {
            return "application/x-tads";
        }
        if (fileName.endsWith(".gbr")) {
            return "application/rpki-ghostbusters";
        }
        if (fileName.endsWith(".gca")) {
            return "application/x-gca-compressed";
        }
        if (fileName.endsWith(".gdl")) {
            return "model/vnd.gdl";
        }
        if (fileName.endsWith(".geo")) {
            return "application/vnd.dynageo";
        }
        if (fileName.endsWith(".gex")) {
            return "application/vnd.geometry-explorer";
        }
        if (fileName.endsWith(".ggb")) {
            return "application/vnd.geogebra.file";
        }
        if (fileName.endsWith(".ggt")) {
            return "application/vnd.geogebra.tool";
        }
        if (fileName.endsWith(".ghf")) {
            return "application/vnd.groove-help";
        }
        if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        if (fileName.endsWith(".gim")) {
            return "application/vnd.groove-identity-message";
        }
        if (fileName.endsWith(".gml")) {
            return "application/gml+xml";
        }
        if (fileName.endsWith(".gmx")) {
            return "application/vnd.gmx";
        }
        if (fileName.endsWith(".gnumeric")) {
            return "application/x-gnumeric";
        }
        if (fileName.endsWith(".gph")) {
            return "application/vnd.flographit";
        }
        if (fileName.endsWith(".gpx")) {
            return "application/gpx+xml";
        }
        if (fileName.endsWith(".gqf")) {
            return "application/vnd.grafeq";
        }
        if (fileName.endsWith(".gqs")) {
            return "application/vnd.grafeq";
        }
        if (fileName.endsWith(".gram")) {
            return "application/srgs";
        }
        if (fileName.endsWith(".gramps")) {
            return "application/x-gramps-xml";
        }
        if (fileName.endsWith(".gre")) {
            return "application/vnd.geometry-explorer";
        }
        if (fileName.endsWith(".grv")) {
            return "application/vnd.groove-injector";
        }
        if (fileName.endsWith(".grxml")) {
            return "application/srgs+xml";
        }
        if (fileName.endsWith(".gsf")) {
            return "application/x-font-ghostscript";
        }
        if (fileName.endsWith(".gtar")) {
            return "application/x-gtar";
        }
        if (fileName.endsWith(".gtm")) {
            return "application/vnd.groove-tool-message";
        }
        if (fileName.endsWith(".gtw")) {
            return "model/vnd.gtw";
        }
        if (fileName.endsWith(".gv")) {
            return "text/vnd.graphviz";
        }
        if (fileName.endsWith(".gxf")) {
            return "application/gxf";
        }
        if (fileName.endsWith(".gxt")) {
            return "application/vnd.geonext";
        }
        if (fileName.endsWith(".gz")) {
            return "application/x-gzip";
        }
        if (fileName.endsWith(".h")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".h261")) {
            return "video/h261";
        }
        if (fileName.endsWith(".h263")) {
            return "video/h263";
        }
        if (fileName.endsWith(".h264")) {
            return "video/h264";
        }
        if (fileName.endsWith(".hal")) {
            return "application/vnd.hal+xml";
        }
        if (fileName.endsWith(".hbci")) {
            return "application/vnd.hbci";
        }
        if (fileName.endsWith(".hdf")) {
            return "application/x-hdf";
        }
        if (fileName.endsWith(".hh")) {
            return "text/x-c";
        }
        if (fileName.endsWith(".hlp")) {
            return "application/winhlp";
        }
        if (fileName.endsWith(".hpgl")) {
            return "application/vnd.hp-hpgl";
        }
        if (fileName.endsWith(".hpid")) {
            return "application/vnd.hp-hpid";
        }
        if (fileName.endsWith(".hps")) {
            return "application/vnd.hp-hps";
        }
        if (fileName.endsWith(".hqx")) {
            return "application/mac-binhex40";
        }
        if (fileName.endsWith(".htc")) {
            return "text/x-component";
        }
        if (fileName.endsWith(".htke")) {
            return "application/vnd.kenameaapp";
        }
        if (fileName.endsWith(".htm")) {
            return "text/html";
        }
        if (fileName.endsWith(".html")) {
            return "text/html";
        }
        if (fileName.endsWith(".hvd")) {
            return "application/vnd.yamaha.hv-dic";
        }
        if (fileName.endsWith(".hvp")) {
            return "application/vnd.yamaha.hv-voice";
        }
        if (fileName.endsWith(".hvs")) {
            return "application/vnd.yamaha.hv-script";
        }
        if (fileName.endsWith(".i2g")) {
            return "application/vnd.intergeo";
        }
        if (fileName.endsWith(".icc")) {
            return "application/vnd.iccprofile";
        }
        if (fileName.endsWith(".ice")) {
            return "x-conference/x-cooltalk";
        }
        if (fileName.endsWith(".icm")) {
            return "application/vnd.iccprofile";
        }
        if (fileName.endsWith(".ico")) {
            return "image/x-icon";
        }
        if (fileName.endsWith(".ics")) {
            return "text/calendar";
        }
        if (fileName.endsWith(".ief")) {
            return "image/ief";
        }
        if (fileName.endsWith(".ifb")) {
            return "text/calendar";
        }
        if (fileName.endsWith(".ifm")) {
            return "application/vnd.shana.informed.formdata";
        }
        if (fileName.endsWith(".iges")) {
            return "model/iges";
        }
        if (fileName.endsWith(".igl")) {
            return "application/vnd.igloader";
        }
        if (fileName.endsWith(".igm")) {
            return "application/vnd.insors.igm";
        }
        if (fileName.endsWith(".igs")) {
            return "model/iges";
        }
        if (fileName.endsWith(".igx")) {
            return "application/vnd.micrografx.igx";
        }
        if (fileName.endsWith(".iif")) {
            return "application/vnd.shana.informed.interchange";
        }
        if (fileName.endsWith(".imp")) {
            return "application/vnd.accpac.simply.imp";
        }
        if (fileName.endsWith(".ims")) {
            return "application/vnd.ms-ims";
        }
        if (fileName.endsWith(".in")) {
            return "text/plain";
        }
        if (fileName.endsWith(".ink")) {
            return "application/inkml+xml";
        }
        if (fileName.endsWith(".inkml")) {
            return "application/inkml+xml";
        }
        if (fileName.endsWith(".install")) {
            return "application/x-install-instructions";
        }
        if (fileName.endsWith(".iota")) {
            return "application/vnd.astraea-software.iota";
        }
        if (fileName.endsWith(".ipfix")) {
            return "application/ipfix";
        }
        if (fileName.endsWith(".ipk")) {
            return "application/vnd.shana.informed.package";
        }
        if (fileName.endsWith(".irm")) {
            return "application/vnd.ibm.rights-management";
        }
        if (fileName.endsWith(".irp")) {
            return "application/vnd.irepository.package+xml";
        }
        if (fileName.endsWith(".iso")) {
            return "application/x-iso9660-image";
        }
        if (fileName.endsWith(".itp")) {
            return "application/vnd.shana.informed.formtemplate";
        }
        if (fileName.endsWith(".ivp")) {
            return "application/vnd.immervision-ivp";
        }
        if (fileName.endsWith(".ivu")) {
            return "application/vnd.immervision-ivu";
        }
        if (fileName.endsWith(".jad")) {
            return "text/vnd.sun.j2me.app-descriptor";
        }
        if (fileName.endsWith(".jam")) {
            return "application/vnd.jam";
        }
        if (fileName.endsWith(".jar")) {
            return "application/java-archive";
        }
        if (fileName.endsWith(".java")) {
            return "text/x-java-source";
        }
        if (fileName.endsWith(".jisp")) {
            return "application/vnd.jisp";
        }
        if (fileName.endsWith(".jlt")) {
            return "application/vnd.hp-jlyt";
        }
        if (fileName.endsWith(".jnlp")) {
            return "application/x-java-jnlp-file";
        }
        if (fileName.endsWith(".joda")) {
            return "application/vnd.joost.joda-archive";
        }
        if (fileName.endsWith(".jpe")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".jpg")) {
            return "image/jpeg";
        }
        if (fileName.endsWith(".jpgm")) {
            return "video/jpm";
        }
        if (fileName.endsWith(".jpgv")) {
            return "video/jpeg";
        }
        if (fileName.endsWith(".jpm")) {
            return "video/jpm";
        }
        if (fileName.endsWith(".js")) {
            return "application/javascript";
        }
        if (fileName.endsWith(".jsf")) {
            return "text/plain";
        }
        if (fileName.endsWith(".json")) {
            return "application/json";
        }
        if (fileName.endsWith(".jsonml")) {
            return "application/jsonml+json";
        }
        if (fileName.endsWith(".jspf")) {
            return "text/plain";
        }
        if (fileName.endsWith(".kar")) {
            return "audio/midi";
        }
        if (fileName.endsWith(".karbon")) {
            return "application/vnd.kde.karbon";
        }
        if (fileName.endsWith(".kfo")) {
            return "application/vnd.kde.kformula";
        }
        if (fileName.endsWith(".kia")) {
            return "application/vnd.kidspiration";
        }
        if (fileName.endsWith(".kml")) {
            return "application/vnd.google-earth.kml+xml";
        }
        if (fileName.endsWith(".kmz")) {
            return "application/vnd.google-earth.kmz";
        }
        if (fileName.endsWith(".kne")) {
            return "application/vnd.kinar";
        }
        if (fileName.endsWith(".knp")) {
            return "application/vnd.kinar";
        }
        if (fileName.endsWith(".kon")) {
            return "application/vnd.kde.kontour";
        }
        if (fileName.endsWith(".kpr")) {
            return "application/vnd.kde.kpresenter";
        }
        if (fileName.endsWith(".kpt")) {
            return "application/vnd.kde.kpresenter";
        }
        if (fileName.endsWith(".kpxx")) {
            return "application/vnd.ds-keypoint";
        }
        if (fileName.endsWith(".ksp")) {
            return "application/vnd.kde.kspread";
        }
        if (fileName.endsWith(".ktr")) {
            return "application/vnd.kahootz";
        }
        if (fileName.endsWith(".ktx")) {
            return "image/ktx";
        }
        if (fileName.endsWith(".ktz")) {
            return "application/vnd.kahootz";
        }
        if (fileName.endsWith(".kwd")) {
            return "application/vnd.kde.kword";
        }
        if (fileName.endsWith(".kwt")) {
            return "application/vnd.kde.kword";
        }
        if (fileName.endsWith(".lasxml")) {
            return "application/vnd.las.las+xml";
        }
        if (fileName.endsWith(".latex")) {
            return "application/x-latex";
        }
        if (fileName.endsWith(".lbd")) {
            return "application/vnd.llamagraphics.life-balance.desktop";
        }
        if (fileName.endsWith(".lbe")) {
            return "application/vnd.llamagraphics.life-balance.exchange+xml";
        }
        if (fileName.endsWith(".les")) {
            return "application/vnd.hhe.lesson-player";
        }
        if (fileName.endsWith(".lha")) {
            return "application/x-lzh-compressed";
        }
        if (fileName.endsWith(".link66")) {
            return "application/vnd.route66.link66+xml";
        }
        if (fileName.endsWith(".list")) {
            return "text/plain";
        }
        if (fileName.endsWith(".list3820")) {
            return "application/vnd.ibm.modcap";
        }
        if (fileName.endsWith(".listafp")) {
            return "application/vnd.ibm.modcap";
        }
        if (fileName.endsWith(".lnk")) {
            return "application/x-ms-shortcut";
        }
        if (fileName.endsWith(".log")) {
            return "text/plain";
        }
        if (fileName.endsWith(".lostxml")) {
            return "application/lost+xml";
        }
        if (fileName.endsWith(".lrf")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".lrm")) {
            return "application/vnd.ms-lrm";
        }
        if (fileName.endsWith(".ltf")) {
            return "application/vnd.frogans.ltf";
        }
        if (fileName.endsWith(".lvp")) {
            return "audio/vnd.lucent.voice";
        }
        if (fileName.endsWith(".lwp")) {
            return "application/vnd.lotus-wordpro";
        }
        if (fileName.endsWith(".lzh")) {
            return "application/x-lzh-compressed";
        }
        if (fileName.endsWith(".m13")) {
            return "application/x-msmediaview";
        }
        if (fileName.endsWith(".m14")) {
            return "application/x-msmediaview";
        }
        if (fileName.endsWith(".m1v")) {
            return "video/mpeg";
        }
        if (fileName.endsWith(".m21")) {
            return "application/mp21";
        }
        if (fileName.endsWith(".m2a")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".m2v")) {
            return "video/mpeg";
        }
        if (fileName.endsWith(".m3a")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".m3u")) {
            return "audio/x-mpegurl";
        }
        if (fileName.endsWith(".m3u8")) {
            return "application/vnd.apple.mpegurl";
        }
        if (fileName.endsWith(".m4a")) {
            return "audio/mp4";
        }
        if (fileName.endsWith(".m4b")) {
            return "audio/mp4";
        }
        if (fileName.endsWith(".m4r")) {
            return "audio/mp4";
        }
        if (fileName.endsWith(".m4u")) {
            return "video/vnd.mpegurl";
        }
        if (fileName.endsWith(".m4v")) {
            return "video/mp4";
        }
        if (fileName.endsWith(".ma")) {
            return "application/mathematica";
        }
        if (fileName.endsWith(".mac")) {
            return "image/x-macpaint";
        }
        if (fileName.endsWith(".mads")) {
            return "application/mads+xml";
        }
        if (fileName.endsWith(".mag")) {
            return "application/vnd.ecowin.chart";
        }
        if (fileName.endsWith(".maker")) {
            return "application/vnd.framemaker";
        }
        if (fileName.endsWith(".man")) {
            return "text/troff";
        }
        if (fileName.endsWith(".mar")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".mathml")) {
            return "application/mathml+xml";
        }
        if (fileName.endsWith(".mb")) {
            return "application/mathematica";
        }
        if (fileName.endsWith(".mbk")) {
            return "application/vnd.mobius.mbk";
        }
        if (fileName.endsWith(".mbox")) {
            return "application/mbox";
        }
        if (fileName.endsWith(".mc1")) {
            return "application/vnd.medcalcdata";
        }
        if (fileName.endsWith(".mcd")) {
            return "application/vnd.mcd";
        }
        if (fileName.endsWith(".mcurl")) {
            return "text/vnd.curl.mcurl";
        }
        if (fileName.endsWith(".mdb")) {
            return "application/x-msaccess";
        }
        if (fileName.endsWith(".mdi")) {
            return "image/vnd.ms-modi";
        }
        if (fileName.endsWith(".me")) {
            return "text/troff";
        }
        if (fileName.endsWith(".mesh")) {
            return "model/mesh";
        }
        if (fileName.endsWith(".meta4")) {
            return "application/metalink4+xml";
        }
        if (fileName.endsWith(".metalink")) {
            return "application/metalink+xml";
        }
        if (fileName.endsWith(".mets")) {
            return "application/mets+xml";
        }
        if (fileName.endsWith(".mfm")) {
            return "application/vnd.mfmp";
        }
        if (fileName.endsWith(".mft")) {
            return "application/rpki-manifest";
        }
        if (fileName.endsWith(".mgp")) {
            return "application/vnd.osgeo.mapguide.package";
        }
        if (fileName.endsWith(".mgz")) {
            return "application/vnd.proteus.magazine";
        }
        if (fileName.endsWith(".mid")) {
            return "audio/midi";
        }
        if (fileName.endsWith(".midi")) {
            return "audio/midi";
        }
        if (fileName.endsWith(".mie")) {
            return "application/x-mie";
        }
        if (fileName.endsWith(".mif")) {
            return "application/x-mif";
        }
        if (fileName.endsWith(".mime")) {
            return "message/rfc822";
        }
        if (fileName.endsWith(".mj2")) {
            return "video/mj2";
        }
        if (fileName.endsWith(".mjp2")) {
            return "video/mj2";
        }
        if (fileName.endsWith(".mk3d")) {
            return "video/x-matroska";
        }
        if (fileName.endsWith(".mka")) {
            return "audio/x-matroska";
        }
        if (fileName.endsWith(".mks")) {
            return "video/x-matroska";
        }
        if (fileName.endsWith(".mkv")) {
            return "video/x-matroska";
        }
        if (fileName.endsWith(".mlp")) {
            return "application/vnd.dolby.mlp";
        }
        if (fileName.endsWith(".mmd")) {
            return "application/vnd.chipnuts.karaoke-mmd";
        }
        if (fileName.endsWith(".mmf")) {
            return "application/vnd.smaf";
        }
        if (fileName.endsWith(".mmr")) {
            return "image/vnd.fujixerox.edmics-mmr";
        }
        if (fileName.endsWith(".mng")) {
            return "video/x-mng";
        }
        if (fileName.endsWith(".mny")) {
            return "application/x-msmoney";
        }
        if (fileName.endsWith(".mobi")) {
            return "application/x-mobipocket-ebook";
        }
        if (fileName.endsWith(".mods")) {
            return "application/mods+xml";
        }
        if (fileName.endsWith(".mov")) {
            return "video/quicktime";
        }
        if (fileName.endsWith(".movie")) {
            return "video/x-sgi-movie";
        }
        if (fileName.endsWith(".mp1")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mp2")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mp21")) {
            return "application/mp21";
        }
        if (fileName.endsWith(".mp2a")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mp3")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mp4")) {
            return "video/mp4";
        }
        if (fileName.endsWith(".mp4a")) {
            return "audio/mp4";
        }
        if (fileName.endsWith(".mp4s")) {
            return "application/mp4";
        }
        if (fileName.endsWith(".mp4v")) {
            return "video/mp4";
        }
        if (fileName.endsWith(".mpa")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mpc")) {
            return "application/vnd.mophun.certificate";
        }
        if (fileName.endsWith(".mpe")) {
            return "video/mpeg";
        }
        if (fileName.endsWith(".mpeg")) {
            return "video/mpeg";
        }
        if (fileName.endsWith(".mpega")) {
            return "audio/x-mpeg";
        }
        if (fileName.endsWith(".mpg")) {
            return "video/mpeg";
        }
        if (fileName.endsWith(".mpg4")) {
            return "video/mp4";
        }
        if (fileName.endsWith(".mpga")) {
            return "audio/mpeg";
        }
        if (fileName.endsWith(".mpkg")) {
            return "application/vnd.apple.installer+xml";
        }
        if (fileName.endsWith(".mpm")) {
            return "application/vnd.blueice.multipass";
        }
        if (fileName.endsWith(".mpn")) {
            return "application/vnd.mophun.application";
        }
        if (fileName.endsWith(".mpp")) {
            return "application/vnd.ms-project";
        }
        if (fileName.endsWith(".mpt")) {
            return "application/vnd.ms-project";
        }
        if (fileName.endsWith(".mpv2")) {
            return "video/mpeg2";
        }
        if (fileName.endsWith(".mpy")) {
            return "application/vnd.ibm.minipay";
        }
        if (fileName.endsWith(".mqy")) {
            return "application/vnd.mobius.mqy";
        }
        if (fileName.endsWith(".mrc")) {
            return "application/marc";
        }
        if (fileName.endsWith(".mrcx")) {
            return "application/marcxml+xml";
        }
        if (fileName.endsWith(".ms")) {
            return "text/troff";
        }
        if (fileName.endsWith(".mscml")) {
            return "application/mediaservercontrol+xml";
        }
        if (fileName.endsWith(".mseed")) {
            return "application/vnd.fdsn.mseed";
        }
        if (fileName.endsWith(".mseq")) {
            return "application/vnd.mseq";
        }
        if (fileName.endsWith(".msf")) {
            return "application/vnd.epson.msf";
        }
        if (fileName.endsWith(".msh")) {
            return "model/mesh";
        }
        if (fileName.endsWith(".msi")) {
            return "application/x-msdownload";
        }
        if (fileName.endsWith(".msl")) {
            return "application/vnd.mobius.msl";
        }
        if (fileName.endsWith(".msty")) {
            return "application/vnd.muvee.style";
        }
        if (fileName.endsWith(".mts")) {
            return "model/vnd.mts";
        }
        if (fileName.endsWith(".mus")) {
            return "application/vnd.musician";
        }
        if (fileName.endsWith(".musicxml")) {
            return "application/vnd.recordare.musicxml+xml";
        }
        if (fileName.endsWith(".mvb")) {
            return "application/x-msmediaview";
        }
        if (fileName.endsWith(".mwf")) {
            return "application/vnd.mfer";
        }
        if (fileName.endsWith(".mxf")) {
            return "application/mxf";
        }
        if (fileName.endsWith(".mxl")) {
            return "application/vnd.recordare.musicxml";
        }
        if (fileName.endsWith(".mxml")) {
            return "application/xv+xml";
        }
        if (fileName.endsWith(".mxs")) {
            return "application/vnd.triscape.mxs";
        }
        if (fileName.endsWith(".mxu")) {
            return "video/vnd.mpegurl";
        }
        if (fileName.endsWith(".n-gage")) {
            return "application/vnd.nokia.n-gage.symbian.install";
        }
        if (fileName.endsWith(".n3")) {
            return "text/n3";
        }
        if (fileName.endsWith(".nb")) {
            return "application/mathematica";
        }
        if (fileName.endsWith(".nbp")) {
            return "application/vnd.wolfram.player";
        }
        if (fileName.endsWith(".nc")) {
            return "application/x-netcdf";
        }
        if (fileName.endsWith(".ncx")) {
            return "application/x-dtbncx+xml";
        }
        if (fileName.endsWith(".nfo")) {
            return "text/x-nfo";
        }
        if (fileName.endsWith(".ngdat")) {
            return "application/vnd.nokia.n-gage.data";
        }
        if (fileName.endsWith(".nitf")) {
            return "application/vnd.nitf";
        }
        if (fileName.endsWith(".nlu")) {
            return "application/vnd.neurolanguage.nlu";
        }
        if (fileName.endsWith(".nml")) {
            return "application/vnd.enliven";
        }
        if (fileName.endsWith(".nnd")) {
            return "application/vnd.noblenet-directory";
        }
        if (fileName.endsWith(".nns")) {
            return "application/vnd.noblenet-sealer";
        }
        if (fileName.endsWith(".nnw")) {
            return "application/vnd.noblenet-web";
        }
        if (fileName.endsWith(".npx")) {
            return "image/vnd.net-fpx";
        }
        if (fileName.endsWith(".nsc")) {
            return "application/x-conference";
        }
        if (fileName.endsWith(".nsf")) {
            return "application/vnd.lotus-notes";
        }
        if (fileName.endsWith(".ntf")) {
            return "application/vnd.nitf";
        }
        if (fileName.endsWith(".nzb")) {
            return "application/x-nzb";
        }
        if (fileName.endsWith(".oa2")) {
            return "application/vnd.fujitsu.oasys2";
        }
        if (fileName.endsWith(".oa3")) {
            return "application/vnd.fujitsu.oasys3";
        }
        if (fileName.endsWith(".oas")) {
            return "application/vnd.fujitsu.oasys";
        }
        if (fileName.endsWith(".obd")) {
            return "application/x-msbinder";
        }
        if (fileName.endsWith(".obj")) {
            return "application/x-tgif";
        }
        if (fileName.endsWith(".oda")) {
            return "application/oda";
        }
        if (fileName.endsWith(".odb")) {
            return "application/vnd.oasis.opendocument.database";
        }
        if (fileName.endsWith(".odc")) {
            return "application/vnd.oasis.opendocument.chart";
        }
        if (fileName.endsWith(".odf")) {
            return "application/vnd.oasis.opendocument.formula";
        }
        if (fileName.endsWith(".odft")) {
            return "application/vnd.oasis.opendocument.formula-template";
        }
        if (fileName.endsWith(".odg")) {
            return "application/vnd.oasis.opendocument.graphics";
        }
        if (fileName.endsWith(".odi")) {
            return "application/vnd.oasis.opendocument.image";
        }
        if (fileName.endsWith(".odm")) {
            return "application/vnd.oasis.opendocument.text-master";
        }
        if (fileName.endsWith(".odp")) {
            return "application/vnd.oasis.opendocument.presentation";
        }
        if (fileName.endsWith(".ods")) {
            return "application/vnd.oasis.opendocument.spreadsheet";
        }
        if (fileName.endsWith(".odt")) {
            return "application/vnd.oasis.opendocument.text";
        }
        if (fileName.endsWith(".oga")) {
            return "audio/ogg";
        }
        if (fileName.endsWith(".ogg")) {
            return "audio/ogg";
        }
        if (fileName.endsWith(".ogv")) {
            return "video/ogg";
        }
        if (fileName.endsWith(".ogx")) {
            return "application/ogg";
        }
        if (fileName.endsWith(".omdoc")) {
            return "application/omdoc+xml";
        }
        if (fileName.endsWith(".onepkg")) {
            return "application/onenote";
        }
        if (fileName.endsWith(".onetmp")) {
            return "application/onenote";
        }
        if (fileName.endsWith(".onetoc")) {
            return "application/onenote";
        }
        if (fileName.endsWith(".onetoc2")) {
            return "application/onenote";
        }
        if (fileName.endsWith(".opf")) {
            return "application/oebps-package+xml";
        }
        if (fileName.endsWith(".opml")) {
            return "text/x-opml";
        }
        if (fileName.endsWith(".oprc")) {
            return "application/vnd.palm";
        }
        if (fileName.endsWith(".org")) {
            return "application/vnd.lotus-organizer";
        }
        if (fileName.endsWith(".osf")) {
            return "application/vnd.yamaha.openscoreformat";
        }
        if (fileName.endsWith(".osfpvg")) {
            return "application/vnd.yamaha.openscoreformat.osfpvg+xml";
        }
        if (fileName.endsWith(".otc")) {
            return "application/vnd.oasis.opendocument.chart-template";
        }
        if (fileName.endsWith(".otf")) {
            return "application/x-font-otf";
        }
        if (fileName.endsWith(".otg")) {
            return "application/vnd.oasis.opendocument.graphics-template";
        }
        if (fileName.endsWith(".oth")) {
            return "application/vnd.oasis.opendocument.text-web";
        }
        if (fileName.endsWith(".oti")) {
            return "application/vnd.oasis.opendocument.image-template";
        }
        if (fileName.endsWith(".otp")) {
            return "application/vnd.oasis.opendocument.presentation-template";
        }
        if (fileName.endsWith(".ots")) {
            return "application/vnd.oasis.opendocument.spreadsheet-template";
        }
        if (fileName.endsWith(".ott")) {
            return "application/vnd.oasis.opendocument.text-template";
        }
        if (fileName.endsWith(".oxps")) {
            return "application/oxps";
        }
        if (fileName.endsWith(".oxt")) {
            return "application/vnd.openofficeorg.extension";
        }
        if (fileName.endsWith(".p")) {
            return "text/x-pascal";
        }
        if (fileName.endsWith(".p10")) {
            return "application/pkcs10";
        }
        if (fileName.endsWith(".p12")) {
            return "application/x-pkcs12";
        }
        if (fileName.endsWith(".p7b")) {
            return "application/x-pkcs7-certificates";
        }
        if (fileName.endsWith(".p7c")) {
            return "application/pkcs7-mime";
        }
        if (fileName.endsWith(".p7m")) {
            return "application/pkcs7-mime";
        }
        if (fileName.endsWith(".p7r")) {
            return "application/x-pkcs7-certreqresp";
        }
        if (fileName.endsWith(".p7s")) {
            return "application/pkcs7-signature";
        }
        if (fileName.endsWith(".p8")) {
            return "application/pkcs8";
        }
        if (fileName.endsWith(".pas")) {
            return "text/x-pascal";
        }
        if (fileName.endsWith(".paw")) {
            return "application/vnd.pawaafile";
        }
        if (fileName.endsWith(".pbd")) {
            return "application/vnd.powerbuilder6";
        }
        if (fileName.endsWith(".pbm")) {
            return "image/x-portable-bitmap";
        }
        if (fileName.endsWith(".pcap")) {
            return "application/vnd.tcpdump.pcap";
        }
        if (fileName.endsWith(".pcf")) {
            return "application/x-font-pcf";
        }
        if (fileName.endsWith(".pcl")) {
            return "application/vnd.hp-pcl";
        }
        if (fileName.endsWith(".pclxl")) {
            return "application/vnd.hp-pclxl";
        }
        if (fileName.endsWith(".pct")) {
            return "image/pict";
        }
        if (fileName.endsWith(".pcurl")) {
            return "application/vnd.curl.pcurl";
        }
        if (fileName.endsWith(".pcx")) {
            return "image/x-pcx";
        }
        if (fileName.endsWith(".pdb")) {
            return "application/vnd.palm";
        }
        if (fileName.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (fileName.endsWith(".pfa")) {
            return "application/x-font-type1";
        }
        if (fileName.endsWith(".pfb")) {
            return "application/x-font-type1";
        }
        if (fileName.endsWith(".pfm")) {
            return "application/x-font-type1";
        }
        if (fileName.endsWith(".pfr")) {
            return "application/font-tdpfr";
        }
        if (fileName.endsWith(".pfx")) {
            return "application/x-pkcs12";
        }
        if (fileName.endsWith(".pgm")) {
            return "image/x-portable-graymap";
        }
        if (fileName.endsWith(".pgn")) {
            return "application/x-chess-pgn";
        }
        if (fileName.endsWith(".pgp")) {
            return "application/pgp-encrypted";
        }
        if (fileName.endsWith(".pic")) {
            return "image/pict";
        }
        if (fileName.endsWith(".pict")) {
            return "image/pict";
        }
        if (fileName.endsWith(".pkg")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".pki")) {
            return "application/pkixcmp";
        }
        if (fileName.endsWith(".pkipath")) {
            return "application/pkix-pkipath";
        }
        if (fileName.endsWith(".plb")) {
            return "application/vnd.3gpp.pic-bw-large";
        }
        if (fileName.endsWith(".plc")) {
            return "application/vnd.mobius.plc";
        }
        if (fileName.endsWith(".plf")) {
            return "application/vnd.pocketlearn";
        }
        if (fileName.endsWith(".pls")) {
            return "audio/x-scpls";
        }
        if (fileName.endsWith(".pml")) {
            return "application/vnd.ctc-posml";
        }
        if (fileName.endsWith(".png")) {
            return "image/png";
        }
        if (fileName.endsWith(".pnm")) {
            return "image/x-portable-anymap";
        }
        if (fileName.endsWith(".pnt")) {
            return "image/x-macpaint";
        }
        if (fileName.endsWith(".portpkg")) {
            return "application/vnd.macports.portpkg";
        }
        if (fileName.endsWith(".pot")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileName.endsWith(".potm")) {
            return "application/vnd.ms-powerpoint.template.macroenabled.12";
        }
        if (fileName.endsWith(".potx")) {
            return "application/vnd.openxmlformats-officedocument.presentationml.template";
        }
        if (fileName.endsWith(".ppam")) {
            return "application/vnd.ms-powerpoint.addin.macroenabled.12";
        }
        if (fileName.endsWith(".ppd")) {
            return "application/vnd.cups-ppd";
        }
        if (fileName.endsWith(".ppm")) {
            return "image/x-portable-pixmap";
        }
        if (fileName.endsWith(".pps")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileName.endsWith(".ppsm")) {
            return "application/vnd.ms-powerpoint.slideshow.macroenabled.12";
        }
        if (fileName.endsWith(".ppsx")) {
            return "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
        }
        if (fileName.endsWith(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (fileName.endsWith(".pptm")) {
            return "application/vnd.ms-powerpoint.presentation.macroenabled.12";
        }
        if (fileName.endsWith(".pptx")) {
            return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        }
        if (fileName.endsWith(".pqa")) {
            return "application/vnd.palm";
        }
        if (fileName.endsWith(".prc")) {
            return "application/x-mobipocket-ebook";
        }
        if (fileName.endsWith(".pre")) {
            return "application/vnd.lotus-freelance";
        }
        if (fileName.endsWith(".prf")) {
            return "application/pics-rules";
        }
        if (fileName.endsWith(".ps")) {
            return "application/postscript";
        }
        if (fileName.endsWith(".psb")) {
            return "application/vnd.3gpp.pic-bw-small";
        }
        if (fileName.endsWith(".psd")) {
            return "image/vnd.adobe.photoshop";
        }
        if (fileName.endsWith(".psf")) {
            return "application/x-font-linux-psf";
        }
        if (fileName.endsWith(".pskcxml")) {
            return "application/pskc+xml";
        }
        if (fileName.endsWith(".ptid")) {
            return "application/vnd.pvi.ptid1";
        }
        if (fileName.endsWith(".pub")) {
            return "application/x-mspublisher";
        }
        if (fileName.endsWith(".pvb")) {
            return "application/vnd.3gpp.pic-bw-var";
        }
        if (fileName.endsWith(".pwn")) {
            return "application/vnd.3m.post-it-notes";
        }
        if (fileName.endsWith(".pya")) {
            return "audio/vnd.ms-playready.media.pya";
        }
        if (fileName.endsWith(".pyv")) {
            return "video/vnd.ms-playready.media.pyv";
        }
        if (fileName.endsWith(".qam")) {
            return "application/vnd.epson.quickanime";
        }
        if (fileName.endsWith(".qbo")) {
            return "application/vnd.intu.qbo";
        }
        if (fileName.endsWith(".qfx")) {
            return "application/vnd.intu.qfx";
        }
        if (fileName.endsWith(".qps")) {
            return "application/vnd.publishare-delta-tree";
        }
        if (fileName.endsWith(".qt")) {
            return "video/quicktime";
        }
        if (fileName.endsWith(".qti")) {
            return "image/x-quicktime";
        }
        if (fileName.endsWith(".qtif")) {
            return "image/x-quicktime";
        }
        if (fileName.endsWith(".qwd")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".qwt")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".qxb")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".qxd")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".qxl")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".qxt")) {
            return "application/vnd.quark.quarkxpress";
        }
        if (fileName.endsWith(".ra")) {
            return "audio/x-pn-realaudio";
        }
        if (fileName.endsWith(".ram")) {
            return "audio/x-pn-realaudio";
        }
        if (fileName.endsWith(".rar")) {
            return "application/x-rar-compressed";
        }
        if (fileName.endsWith(".ras")) {
            return "image/x-cmu-raster";
        }
        if (fileName.endsWith(".rcprofile")) {
            return "application/vnd.ipunplugged.rcprofile";
        }
        if (fileName.endsWith(".rdf")) {
            return "application/rdf+xml";
        }
        if (fileName.endsWith(".rdz")) {
            return "application/vnd.data-vision.rdz";
        }
        if (fileName.endsWith(".rep")) {
            return "application/vnd.businessobjects";
        }
        if (fileName.endsWith(".res")) {
            return "application/x-dtbresource+xml";
        }
        if (fileName.endsWith(".rgb")) {
            return "image/x-rgb";
        }
        if (fileName.endsWith(".rif")) {
            return "application/reginfo+xml";
        }
        if (fileName.endsWith(".rip")) {
            return "audio/vnd.rip";
        }
        if (fileName.endsWith(".ris")) {
            return "application/x-research-info-systems";
        }
        if (fileName.endsWith(".rl")) {
            return "application/resource-lists+xml";
        }
        if (fileName.endsWith(".rlc")) {
            return "image/vnd.fujixerox.edmics-rlc";
        }
        if (fileName.endsWith(".rld")) {
            return "application/resource-lists-diff+xml";
        }
        if (fileName.endsWith(".rm")) {
            return "application/vnd.rn-realmedia";
        }
        if (fileName.endsWith(".rmi")) {
            return "audio/midi";
        }
        if (fileName.endsWith(".rmp")) {
            return "audio/x-pn-realaudio-plugin";
        }
        if (fileName.endsWith(".rms")) {
            return "application/vnd.jcp.javame.midlet-rms";
        }
        if (fileName.endsWith(".rmvb")) {
            return "application/vnd.rn-realmedia-vbr";
        }
        if (fileName.endsWith(".rnc")) {
            return "application/relax-ng-compact-syntax";
        }
        if (fileName.endsWith(".roa")) {
            return "application/rpki-roa";
        }
        if (fileName.endsWith(".roff")) {
            return "text/troff";
        }
        if (fileName.endsWith(".rp9")) {
            return "application/vnd.cloanto.rp9";
        }
        if (fileName.endsWith(".rpss")) {
            return "application/vnd.nokia.radio-presets";
        }
        if (fileName.endsWith(".rpst")) {
            return "application/vnd.nokia.radio-preset";
        }
        if (fileName.endsWith(".rq")) {
            return "application/sparql-query";
        }
        if (fileName.endsWith(".rs")) {
            return "application/rls-services+xml";
        }
        if (fileName.endsWith(".rsd")) {
            return "application/rsd+xml";
        }
        if (fileName.endsWith(".rss")) {
            return "application/rss+xml";
        }
        if (fileName.endsWith(".rtf")) {
            return "application/rtf";
        }
        if (fileName.endsWith(".rtx")) {
            return "text/richtext";
        }
        if (fileName.endsWith(".s")) {
            return "text/x-asm";
        }
        if (fileName.endsWith(".s3m")) {
            return "audio/s3m";
        }
        if (fileName.endsWith(".saf")) {
            return "application/vnd.yamaha.smaf-audio";
        }
        if (fileName.endsWith(".sbml")) {
            return "application/sbml+xml";
        }
        if (fileName.endsWith(".sc")) {
            return "application/vnd.ibm.secure-container";
        }
        if (fileName.endsWith(".scd")) {
            return "application/x-msschedule";
        }
        if (fileName.endsWith(".scm")) {
            return "application/vnd.lotus-screencam";
        }
        if (fileName.endsWith(".scq")) {
            return "application/scvp-cv-request";
        }
        if (fileName.endsWith(".scs")) {
            return "application/scvp-cv-response";
        }
        if (fileName.endsWith(".scurl")) {
            return "text/vnd.curl.scurl";
        }
        if (fileName.endsWith(".sda")) {
            return "application/vnd.stardivision.draw";
        }
        if (fileName.endsWith(".sdc")) {
            return "application/vnd.stardivision.calc";
        }
        if (fileName.endsWith(".sdd")) {
            return "application/vnd.stardivision.impress";
        }
        if (fileName.endsWith(".sdkd")) {
            return "application/vnd.solent.sdkm+xml";
        }
        if (fileName.endsWith(".sdkm")) {
            return "application/vnd.solent.sdkm+xml";
        }
        if (fileName.endsWith(".sdp")) {
            return "application/sdp";
        }
        if (fileName.endsWith(".sdw")) {
            return "application/vnd.stardivision.writer";
        }
        if (fileName.endsWith(".see")) {
            return "application/vnd.seemail";
        }
        if (fileName.endsWith(".seed")) {
            return "application/vnd.fdsn.seed";
        }
        if (fileName.endsWith(".sema")) {
            return "application/vnd.sema";
        }
        if (fileName.endsWith(".semd")) {
            return "application/vnd.semd";
        }
        if (fileName.endsWith(".semf")) {
            return "application/vnd.semf";
        }
        if (fileName.endsWith(".ser")) {
            return "application/java-serialized-object";
        }
        if (fileName.endsWith(".setpay")) {
            return "application/set-payment-initiation";
        }
        if (fileName.endsWith(".setreg")) {
            return "application/set-registration-initiation";
        }
        if (fileName.endsWith(".sfd-hdstx")) {
            return "application/vnd.hydrostatix.sof-data";
        }
        if (fileName.endsWith(".sfs")) {
            return "application/vnd.spotfire.sfs";
        }
        if (fileName.endsWith(".sfv")) {
            return "text/x-sfv";
        }
        if (fileName.endsWith(".sgi")) {
            return "image/sgi";
        }
        if (fileName.endsWith(".sgl")) {
            return "application/vnd.stardivision.writer-global";
        }
        if (fileName.endsWith(".sgm")) {
            return "text/sgml";
        }
        if (fileName.endsWith(".sgml")) {
            return "text/sgml";
        }
        if (fileName.endsWith(".sh")) {
            return "application/x-sh";
        }
        if (fileName.endsWith(".shar")) {
            return "application/x-shar";
        }
        if (fileName.endsWith(".shf")) {
            return "application/shf+xml";
        }
        if (fileName.endsWith(".shtml")) {
            return "text/x-server-parsed-html";
        }
        if (fileName.endsWith(".sid")) {
            return "image/x-mrsid-image";
        }
        if (fileName.endsWith(".sig")) {
            return "application/pgp-signature";
        }
        if (fileName.endsWith(".sil")) {
            return "audio/silk";
        }
        if (fileName.endsWith(".silo")) {
            return "model/mesh";
        }
        if (fileName.endsWith(".sis")) {
            return "application/vnd.symbian.install";
        }
        if (fileName.endsWith(".sisx")) {
            return "application/vnd.symbian.install";
        }
        if (fileName.endsWith(".sit")) {
            return "application/x-stuffit";
        }
        if (fileName.endsWith(".sitx")) {
            return "application/x-stuffitx";
        }
        if (fileName.endsWith(".skd")) {
            return "application/vnd.koan";
        }
        if (fileName.endsWith(".skm")) {
            return "application/vnd.koan";
        }
        if (fileName.endsWith(".skp")) {
            return "application/vnd.koan";
        }
        if (fileName.endsWith(".skt")) {
            return "application/vnd.koan";
        }
        if (fileName.endsWith(".sldm")) {
            return "application/vnd.ms-powerpoint.slide.macroenabled.12";
        }
        if (fileName.endsWith(".sldx")) {
            return "application/vnd.openxmlformats-officedocument.presentationml.slide";
        }
        if (fileName.endsWith(".slt")) {
            return "application/vnd.epson.salt";
        }
        if (fileName.endsWith(".sm")) {
            return "application/vnd.stepmania.stepchart";
        }
        if (fileName.endsWith(".smf")) {
            return "application/vnd.stardivision.math";
        }
        if (fileName.endsWith(".smi")) {
            return "application/smil+xml";
        }
        if (fileName.endsWith(".smil")) {
            return "application/smil+xml";
        }
        if (fileName.endsWith(".smv")) {
            return "video/x-smv";
        }
        if (fileName.endsWith(".smzip")) {
            return "application/vnd.stepmania.package";
        }
        if (fileName.endsWith(".snd")) {
            return "audio/basic";
        }
        if (fileName.endsWith(".snf")) {
            return "application/x-font-snf";
        }
        if (fileName.endsWith(".so")) {
            return "application/octet-stream";
        }
        if (fileName.endsWith(".spc")) {
            return "application/x-pkcs7-certificates";
        }
        if (fileName.endsWith(".spf")) {
            return "application/vnd.yamaha.smaf-phrase";
        }
        if (fileName.endsWith(".spl")) {
            return "application/x-futuresplash";
        }
        if (fileName.endsWith(".spot")) {
            return "text/vnd.in3d.spot";
        }
        if (fileName.endsWith(".spp")) {
            return "application/scvp-vp-response";
        }
        if (fileName.endsWith(".spq")) {
            return "application/scvp-vp-request";
        }
        if (fileName.endsWith(".spx")) {
            return "audio/ogg";
        }
        if (fileName.endsWith(".sql")) {
            return "application/x-sql";
        }
        if (fileName.endsWith(".src")) {
            return "application/x-wais-source";
        }
        if (fileName.endsWith(".srt")) {
            return "application/x-subrip";
        }
        if (fileName.endsWith(".sru")) {
            return "application/sru+xml";
        }
        if (fileName.endsWith(".srx")) {
            return "application/sparql-results+xml";
        }
        if (fileName.endsWith(".ssdl")) {
            return "application/ssdl+xml";
        }
        if (fileName.endsWith(".sse")) {
            return "application/vnd.kodak-descriptor";
        }
        if (fileName.endsWith(".ssf")) {
            return "application/vnd.epson.ssf";
        }
        if (fileName.endsWith(".ssml")) {
            return "application/ssml+xml";
        }
        if (fileName.endsWith(".st")) {
            return "application/vnd.sailingtracker.track";
        }
        if (fileName.endsWith(".stc")) {
            return "application/vnd.sun.xml.calc.template";
        }
        if (fileName.endsWith(".std")) {
            return "application/vnd.sun.xml.draw.template";
        }
        if (fileName.endsWith(".stf")) {
            return "application/vnd.wt.stf";
        }
        if (fileName.endsWith(".sti")) {
            return "application/vnd.sun.xml.impress.template";
        }
        if (fileName.endsWith(".stk")) {
            return "application/hyperstudio";
        }
        if (fileName.endsWith(".stl")) {
            return "application/vnd.ms-pki.stl";
        }
        if (fileName.endsWith(".str")) {
            return "application/vnd.pg.format";
        }
        if (fileName.endsWith(".stw")) {
            return "application/vnd.sun.xml.writer.template";
        }
        if (fileName.endsWith(".sub")) {
            return "text/vnd.dvb.subtitle";
        }
        if (fileName.endsWith(".sus")) {
            return "application/vnd.sus-calendar";
        }
        if (fileName.endsWith(".susp")) {
            return "application/vnd.sus-calendar";
        }
        if (fileName.endsWith(".sv4cpio")) {
            return "application/x-sv4cpio";
        }
        if (fileName.endsWith(".sv4crc")) {
            return "application/x-sv4crc";
        }
        if (fileName.endsWith(".svc")) {
            return "application/vnd.dvb.service";
        }
        if (fileName.endsWith(".svd")) {
            return "application/vnd.svd";
        }
        if (fileName.endsWith(".svg")) {
            return "image/svg+xml";
        }
        if (fileName.endsWith(".svgz")) {
            return "image/svg+xml";
        }
        if (fileName.endsWith(".swa")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".swf")) {
            return "application/x-shockwave-flash";
        }
        if (fileName.endsWith(".swi")) {
            return "application/vnd.aristanetworks.swi";
        }
        if (fileName.endsWith(".sxc")) {
            return "application/vnd.sun.xml.calc";
        }
        if (fileName.endsWith(".sxd")) {
            return "application/vnd.sun.xml.draw";
        }
        if (fileName.endsWith(".sxg")) {
            return "application/vnd.sun.xml.writer.global";
        }
        if (fileName.endsWith(".sxi")) {
            return "application/vnd.sun.xml.impress";
        }
        if (fileName.endsWith(".sxm")) {
            return "application/vnd.sun.xml.math";
        }
        if (fileName.endsWith(".sxw")) {
            return "application/vnd.sun.xml.writer";
        }
        if (fileName.endsWith(".t")) {
            return "text/troff";
        }
        if (fileName.endsWith(".t3")) {
            return "application/x-t3vm-image";
        }
        if (fileName.endsWith(".taglet")) {
            return "application/vnd.mynfc";
        }
        if (fileName.endsWith(".tao")) {
            return "application/vnd.tao.intent-module-archive";
        }
        if (fileName.endsWith(".tar")) {
            return "application/x-tar";
        }
        if (fileName.endsWith(".tcap")) {
            return "application/vnd.3gpp2.tcap";
        }
        if (fileName.endsWith(".tcl")) {
            return "application/x-tcl";
        }
        if (fileName.endsWith(".teacher")) {
            return "application/vnd.smart.teacher";
        }
        if (fileName.endsWith(".tei")) {
            return "application/tei+xml";
        }
        if (fileName.endsWith(".teicorpus")) {
            return "application/tei+xml";
        }
        if (fileName.endsWith(".tex")) {
            return "application/x-tex";
        }
        if (fileName.endsWith(".texi")) {
            return "application/x-texinfo";
        }
        if (fileName.endsWith(".texinfo")) {
            return "application/x-texinfo";
        }
        if (fileName.endsWith(".text")) {
            return "text/plain";
        }
        if (fileName.endsWith(".tfi")) {
            return "application/thraud+xml";
        }
        if (fileName.endsWith(".tfm")) {
            return "application/x-tex-tfm";
        }
        if (fileName.endsWith(".tga")) {
            return "image/x-tga";
        }
        if (fileName.endsWith(".thmx")) {
            return "application/vnd.ms-officetheme";
        }
        if (fileName.endsWith(".tif")) {
            return "image/tiff";
        }
        if (fileName.endsWith(".tiff")) {
            return "image/tiff";
        }
        if (fileName.endsWith(".tmo")) {
            return "application/vnd.tmobile-livetv";
        }
        if (fileName.endsWith(".torrent")) {
            return "application/x-bittorrent";
        }
        if (fileName.endsWith(".tpl")) {
            return "application/vnd.groove-tool-template";
        }
        if (fileName.endsWith(".tpt")) {
            return "application/vnd.trid.tpt";
        }
        if (fileName.endsWith(".tr")) {
            return "text/troff";
        }
        if (fileName.endsWith(".tra")) {
            return "application/vnd.trueapp";
        }
        if (fileName.endsWith(".trm")) {
            return "application/x-msterminal";
        }
        if (fileName.endsWith(".tsd")) {
            return "application/timestamped-data";
        }
        if (fileName.endsWith(".tsv")) {
            return "text/tab-separated-values";
        }
        if (fileName.endsWith(".ttc")) {
            return "application/x-font-ttf";
        }
        if (fileName.endsWith(".ttf")) {
            return "application/x-font-ttf";
        }
        if (fileName.endsWith(".ttl")) {
            return "text/turtle";
        }
        if (fileName.endsWith(".twd")) {
            return "application/vnd.simtech-mindmapper";
        }
        if (fileName.endsWith(".twds")) {
            return "application/vnd.simtech-mindmapper";
        }
        if (fileName.endsWith(".txd")) {
            return "application/vnd.genomatix.tuxedo";
        }
        if (fileName.endsWith(".txf")) {
            return "application/vnd.mobius.txf";
        }
        if (fileName.endsWith(".txt")) {
            return "text/plain";
        }
        if (fileName.endsWith(".u32")) {
            return "application/x-authorware-bin";
        }
        if (fileName.endsWith(".udeb")) {
            return "application/x-debian-package";
        }
        if (fileName.endsWith(".ufd")) {
            return "application/vnd.ufdl";
        }
        if (fileName.endsWith(".ufdl")) {
            return "application/vnd.ufdl";
        }
        if (fileName.endsWith(".ulw")) {
            return "audio/basic";
        }
        if (fileName.endsWith(".ulx")) {
            return "application/x-glulx";
        }
        if (fileName.endsWith(".umj")) {
            return "application/vnd.umajin";
        }
        if (fileName.endsWith(".unityweb")) {
            return "application/vnd.unity";
        }
        if (fileName.endsWith(".uoml")) {
            return "application/vnd.uoml+xml";
        }
        if (fileName.endsWith(".uri")) {
            return "text/uri-list";
        }
        if (fileName.endsWith(".uris")) {
            return "text/uri-list";
        }
        if (fileName.endsWith(".urls")) {
            return "text/uri-list";
        }
        if (fileName.endsWith(".ustar")) {
            return "application/x-ustar";
        }
        if (fileName.endsWith(".utz")) {
            return "application/vnd.uiq.theme";
        }
        if (fileName.endsWith(".uu")) {
            return "text/x-uuencode";
        }
        if (fileName.endsWith(".uva")) {
            return "audio/vnd.dece.audio";
        }
        if (fileName.endsWith(".uvd")) {
            return "application/vnd.dece.data";
        }
        if (fileName.endsWith(".uvf")) {
            return "application/vnd.dece.data";
        }
        if (fileName.endsWith(".uvg")) {
            return "image/vnd.dece.graphic";
        }
        if (fileName.endsWith(".uvh")) {
            return "video/vnd.dece.hd";
        }
        if (fileName.endsWith(".uvi")) {
            return "image/vnd.dece.graphic";
        }
        if (fileName.endsWith(".uvm")) {
            return "video/vnd.dece.mobile";
        }
        if (fileName.endsWith(".uvp")) {
            return "video/vnd.dece.pd";
        }
        if (fileName.endsWith(".uvs")) {
            return "video/vnd.dece.sd";
        }
        if (fileName.endsWith(".uvt")) {
            return "application/vnd.dece.ttml+xml";
        }
        if (fileName.endsWith(".uvu")) {
            return "video/vnd.uvvu.mp4";
        }
        if (fileName.endsWith(".uvv")) {
            return "video/vnd.dece.video";
        }
        if (fileName.endsWith(".uvva")) {
            return "audio/vnd.dece.audio";
        }
        if (fileName.endsWith(".uvvd")) {
            return "application/vnd.dece.data";
        }
        if (fileName.endsWith(".uvvf")) {
            return "application/vnd.dece.data";
        }
        if (fileName.endsWith(".uvvg")) {
            return "image/vnd.dece.graphic";
        }
        if (fileName.endsWith(".uvvh")) {
            return "video/vnd.dece.hd";
        }
        if (fileName.endsWith(".uvvi")) {
            return "image/vnd.dece.graphic";
        }
        if (fileName.endsWith(".uvvm")) {
            return "video/vnd.dece.mobile";
        }
        if (fileName.endsWith(".uvvp")) {
            return "video/vnd.dece.pd";
        }
        if (fileName.endsWith(".uvvs")) {
            return "video/vnd.dece.sd";
        }
        if (fileName.endsWith(".uvvt")) {
            return "application/vnd.dece.ttml+xml";
        }
        if (fileName.endsWith(".uvvu")) {
            return "video/vnd.uvvu.mp4";
        }
        if (fileName.endsWith(".uvvv")) {
            return "video/vnd.dece.video";
        }
        if (fileName.endsWith(".uvvx")) {
            return "application/vnd.dece.unspecified";
        }
        if (fileName.endsWith(".uvvz")) {
            return "application/vnd.dece.zip";
        }
        if (fileName.endsWith(".uvx")) {
            return "application/vnd.dece.unspecified";
        }
        if (fileName.endsWith(".uvz")) {
            return "application/vnd.dece.zip";
        }
        if (fileName.endsWith(".vcard")) {
            return "text/vcard";
        }
        if (fileName.endsWith(".vcd")) {
            return "application/x-cdlink";
        }
        if (fileName.endsWith(".vcf")) {
            return "text/x-vcard";
        }
        if (fileName.endsWith(".vcg")) {
            return "application/vnd.groove-vcard";
        }
        if (fileName.endsWith(".vcs")) {
            return "text/x-vcalendar";
        }
        if (fileName.endsWith(".vcx")) {
            return "application/vnd.vcx";
        }
        if (fileName.endsWith(".vis")) {
            return "application/vnd.visionary";
        }
        if (fileName.endsWith(".viv")) {
            return "video/vnd.vivo";
        }
        if (fileName.endsWith(".vob")) {
            return "video/x-ms-vob";
        }
        if (fileName.endsWith(".vor")) {
            return "application/vnd.stardivision.writer";
        }
        if (fileName.endsWith(".vox")) {
            return "application/x-authorware-bin";
        }
        if (fileName.endsWith(".vrml")) {
            return "model/vrml";
        }
        if (fileName.endsWith(".vsd")) {
            return "application/vnd.visio";
        }
        if (fileName.endsWith(".vsf")) {
            return "application/vnd.vsf";
        }
        if (fileName.endsWith(".vss")) {
            return "application/vnd.visio";
        }
        if (fileName.endsWith(".vst")) {
            return "application/vnd.visio";
        }
        if (fileName.endsWith(".vsw")) {
            return "application/vnd.visio";
        }
        if (fileName.endsWith(".vtu")) {
            return "model/vnd.vtu";
        }
        if (fileName.endsWith(".vxml")) {
            return "application/voicexml+xml";
        }
        if (fileName.endsWith(".w3d")) {
            return "application/x-director";
        }
        if (fileName.endsWith(".wad")) {
            return "application/x-doom";
        }
        if (fileName.endsWith(".wav")) {
            return "audio/x-wav";
        }
        if (fileName.endsWith(".wax")) {
            return "audio/x-ms-wax";
        }
        if (fileName.endsWith(".wbmp")) {
            return "image/vnd.wap.wbmp";
        }
        if (fileName.endsWith(".wbs")) {
            return "application/vnd.criticaltools.wbs+xml";
        }
        if (fileName.endsWith(".wbxml")) {
            return "application/vnd.wap.wbxml";
        }
        if (fileName.endsWith(".wcm")) {
            return "application/vnd.ms-works";
        }
        if (fileName.endsWith(".wdb")) {
            return "application/vnd.ms-works";
        }
        if (fileName.endsWith(".wdp")) {
            return "image/vnd.ms-photo";
        }
        if (fileName.endsWith(".weba")) {
            return "audio/webm";
        }
        if (fileName.endsWith(".webm")) {
            return "video/webm";
        }
        if (fileName.endsWith(".webp")) {
            return "image/webp";
        }
        if (fileName.endsWith(".wg")) {
            return "application/vnd.pmi.widget";
        }
        if (fileName.endsWith(".wgt")) {
            return "application/widget";
        }
        if (fileName.endsWith(".wks")) {
            return "application/vnd.ms-works";
        }
        if (fileName.endsWith(".wm")) {
            return "video/x-ms-wm";
        }
        if (fileName.endsWith(".wma")) {
            return "audio/x-ms-wma";
        }
        if (fileName.endsWith(".wmd")) {
            return "application/x-ms-wmd";
        }
        if (fileName.endsWith(".wmf")) {
            return "application/x-msmetafile";
        }
        if (fileName.endsWith(".wml")) {
            return "text/vnd.wap.wml";
        }
        if (fileName.endsWith(".wmlc")) {
            return "application/vnd.wap.wmlc";
        }
        if (fileName.endsWith(".wmls")) {
            return "text/vnd.wap.wmlscript";
        }
        if (fileName.endsWith(".wmlsc")) {
            return "application/vnd.wap.wmlscriptc";
        }
        if (fileName.endsWith(".wmv")) {
            return "video/x-ms-wmv";
        }
        if (fileName.endsWith(".wmx")) {
            return "video/x-ms-wmx";
        }
        if (fileName.endsWith(".wmz")) {
            return "application/x-msmetafile";
        }
        if (fileName.endsWith(".woff")) {
            return "application/x-font-woff";
        }
        if (fileName.endsWith(".wpd")) {
            return "application/vnd.wordperfect";
        }
        if (fileName.endsWith(".wpl")) {
            return "application/vnd.ms-wpl";
        }
        if (fileName.endsWith(".wps")) {
            return "application/vnd.ms-works";
        }
        if (fileName.endsWith(".wqd")) {
            return "application/vnd.wqd";
        }
        if (fileName.endsWith(".wri")) {
            return "application/x-mswrite";
        }
        if (fileName.endsWith(".wrl")) {
            return "model/vrml";
        }
        if (fileName.endsWith(".wsdl")) {
            return "application/wsdl+xml";
        }
        if (fileName.endsWith(".wspolicy")) {
            return "application/wspolicy+xml";
        }
        if (fileName.endsWith(".wtb")) {
            return "application/vnd.webturbo";
        }
        if (fileName.endsWith(".wvx")) {
            return "video/x-ms-wvx";
        }
        if (fileName.endsWith(".x32")) {
            return "application/x-authorware-bin";
        }
        if (fileName.endsWith(".x3d")) {
            return "model/x3d+xml";
        }
        if (fileName.endsWith(".x3db")) {
            return "model/x3d+binary";
        }
        if (fileName.endsWith(".x3dbz")) {
            return "model/x3d+binary";
        }
        if (fileName.endsWith(".x3dv")) {
            return "model/x3d+vrml";
        }
        if (fileName.endsWith(".x3dvz")) {
            return "model/x3d+vrml";
        }
        if (fileName.endsWith(".x3dz")) {
            return "model/x3d+xml";
        }
        if (fileName.endsWith(".xaml")) {
            return "application/xaml+xml";
        }
        if (fileName.endsWith(".xap")) {
            return "application/x-silverlight-app";
        }
        if (fileName.endsWith(".xar")) {
            return "application/vnd.xara";
        }
        if (fileName.endsWith(".xbap")) {
            return "application/x-ms-xbap";
        }
        if (fileName.endsWith(".xbd")) {
            return "application/vnd.fujixerox.docuworks.binder";
        }
        if (fileName.endsWith(".xbm")) {
            return "image/x-xbitmap";
        }
        if (fileName.endsWith(".xdf")) {
            return "application/xcap-diff+xml";
        }
        if (fileName.endsWith(".xdm")) {
            return "application/vnd.syncml.dm+xml";
        }
        if (fileName.endsWith(".xdp")) {
            return "application/vnd.adobe.xdp+xml";
        }
        if (fileName.endsWith(".xdssc")) {
            return "application/dssc+xml";
        }
        if (fileName.endsWith(".xdw")) {
            return "application/vnd.fujixerox.docuworks";
        }
        if (fileName.endsWith(".xenc")) {
            return "application/xenc+xml";
        }
        if (fileName.endsWith(".xer")) {
            return "application/patch-ops-error+xml";
        }
        if (fileName.endsWith(".xfdf")) {
            return "application/vnd.adobe.xfdf";
        }
        if (fileName.endsWith(".xfdl")) {
            return "application/vnd.xfdl";
        }
        if (fileName.endsWith(".xht")) {
            return "application/xhtml+xml";
        }
        if (fileName.endsWith(".xhtml")) {
            return "application/xhtml+xml";
        }
        if (fileName.endsWith(".xhvml")) {
            return "application/xv+xml";
        }
        if (fileName.endsWith(".xif")) {
            return "image/vnd.xiff";
        }
        if (fileName.endsWith(".xla")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xlam")) {
            return "application/vnd.ms-excel.addin.macroenabled.12";
        }
        if (fileName.endsWith(".xlc")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xlf")) {
            return "application/x-xliff+xml";
        }
        if (fileName.endsWith(".xlm")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xls")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xlsb")) {
            return "application/vnd.ms-excel.sheet.binary.macroenabled.12";
        }
        if (fileName.endsWith(".xlsm")) {
            return "application/vnd.ms-excel.sheet.macroenabled.12";
        }
        if (fileName.endsWith(".xlsx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        }
        if (fileName.endsWith(".xlt")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xltm")) {
            return "application/vnd.ms-excel.template.macroenabled.12";
        }
        if (fileName.endsWith(".xltx")) {
            return "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
        }
        if (fileName.endsWith(".xlw")) {
            return "application/vnd.ms-excel";
        }
        if (fileName.endsWith(".xm")) {
            return "audio/xm";
        }
        if (fileName.endsWith(".xml")) {
            return "application/xml";
        }
        if (fileName.endsWith(".xo")) {
            return "application/vnd.olpc-sugar";
        }
        if (fileName.endsWith(".xop")) {
            return "application/xop+xml";
        }
        if (fileName.endsWith(".xpi")) {
            return "application/x-xpinstall";
        }
        if (fileName.endsWith(".xpl")) {
            return "application/xproc+xml";
        }
        if (fileName.endsWith(".xpm")) {
            return "image/x-xpixmap";
        }
        if (fileName.endsWith(".xpr")) {
            return "application/vnd.is-xpr";
        }
        if (fileName.endsWith(".xps")) {
            return "application/vnd.ms-xpsdocument";
        }
        if (fileName.endsWith(".xpw")) {
            return "application/vnd.intercon.formnet";
        }
        if (fileName.endsWith(".xpx")) {
            return "application/vnd.intercon.formnet";
        }
        if (fileName.endsWith(".xsl")) {
            return "application/xml";
        }
        if (fileName.endsWith(".xslt")) {
            return "application/xslt+xml";
        }
        if (fileName.endsWith(".xsm")) {
            return "application/vnd.syncml+xml";
        }
        if (fileName.endsWith(".xspf")) {
            return "application/xspf+xml";
        }
        if (fileName.endsWith(".xul")) {
            return "application/vnd.mozilla.xul+xml";
        }
        if (fileName.endsWith(".xvm")) {
            return "application/xv+xml";
        }
        if (fileName.endsWith(".xvml")) {
            return "application/xv+xml";
        }
        if (fileName.endsWith(".xwd")) {
            return "image/x-xwindowdump";
        }
        if (fileName.endsWith(".xyz")) {
            return "chemical/x-xyz";
        }
        if (fileName.endsWith(".xz")) {
            return "application/x-xz";
        }
        if (fileName.endsWith(".yang")) {
            return "application/yang";
        }
        if (fileName.endsWith(".yin")) {
            return "application/yin+xml";
        }
        if (fileName.endsWith(".z")) {
            return "application/x-compress";
        }
        if (fileName.endsWith(".Z")) {
            return "application/x-compress";
        }
        if (fileName.endsWith(".z1")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z2")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z3")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z4")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z5")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z6")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z7")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".z8")) {
            return "application/x-zmachine";
        }
        if (fileName.endsWith(".zaz")) {
            return "application/vnd.zzazz.deck+xml";
        }
        if (fileName.endsWith(".zip")) {
            return "application/zip";
        }
        if (fileName.endsWith(".zir")) {
            return "application/vnd.zul";
        }
        if (fileName.endsWith(".zirz")) {
            return "application/vnd.zul";
        }
        if (fileName.endsWith(".zmm")) {
            return "application/vnd.handheld-entertainment+xml";
        }
        return "application/octet­stream";
    }
}
