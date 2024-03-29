import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.logger.LoggerLevel;
import com.microsoft.graph.models.*;
import com.microsoft.graph.requests.DriveItemCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import com.microsoft.graph.requests.PermissionCollectionPage;
import com.microsoft.graph.requests.UserCollectionPage;
import com.microsoft.graph.tasks.IProgressCallback;
import com.microsoft.graph.tasks.LargeFileUploadResult;
import com.microsoft.graph.tasks.LargeFileUploadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//import org.apache.commons.io.IOUtils;

public class Executor {

    public static void main(String[] args) throws IOException {
        String resourceId = "https%3A%2F%2Fgraph.microsoft.com%2F.default";
        String clientId = "0f0f5373-14aa-4679-82d3-37609caa8470";
        String username = "sovieteagle2017@gmail.com";
        String password = "reddivision";
        String client_secret = "tj~tbPMSk82kkv86kxSe-e-57ABfCI_k-3";
        String tenantId ="8442d917-8472-4dc7-8aae-ab698fc91706";

        /////Second registration SomeChange
        //String resourceId = "https%3A%2F%2Fgraph.microsoft.com%2F.default";
        //String username = "sovieteagle2017@gmail.com";
        //String password = "reddivision";
        /*String clientId = "66e431ef-f50c-4efd-9aa3-efdafb0692eb";
        String client_secret = "2fYyD4_r.I-~4h.ZxkB6ht_E91Hj_f3S1P";
        String tenantId ="8442d917-8472-4dc7-8aae-ab698fc91706";*/

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //System.out.println("Before clientSecretCredential");

        final ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(client_secret)
                .tenantId(tenantId)
                .build();
        List<String> scopes = new ArrayList<>();
        scopes.add("https://graph.microsoft.com/.default");
        //System.out.println("After clientSecretCredential");

        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(scopes, clientSecretCredential);
       /* final OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider)
                .newBuilder()
                .followSslRedirects(false) // sample configuration to apply to client
                .build();*/
        //System.out.println("After tokenCredAuthProvider");
        final GraphServiceClient graphClient = GraphServiceClient
                .builder()
                .authenticationProvider(tokenCredAuthProvider)
                //.httpClient(httpClient) //Пока не понятна разница с элементов выше
                .buildClient();
        graphClient.getLogger().setLoggingLevel(LoggerLevel.DEBUG);
        //System.out.println("After graphClient");
        /*final User me = graphClient.me().buildRequest().get();
        System.out.println("ME " + me);*/
        UserCollectionPage users = graphClient.users()
                .buildRequest()
                .get();

//        List<String> asJson = new ArrayList<>();
//        users.getCurrentPage().stream().forEach(user -> {
//            try {
//                asJson.add(objectMapper.writeValueAsString(user));
//            } catch (JsonProcessingException e) {
//                e.printStackTrace();
//            }
//        });
//        System.out.println(asJson);
//        List<User> aUser = users.getCurrentPage();
//        System.out.println(aUser.size());
//        for(User oUser : aUser){
//            Drive oDrive = oUser.drive;
//            System.out.println(oUser.displayName);
//            System.out.println(oDrive);
//            //oDrive.
//        }
        //System.out.println("Drive " + graphClient.drive().buildRequest().toString());
        //System.out.println("Drives " + graphClient.drives().count().buildRequest().get());
        //System.out.println("Directory" + graphClient.directory().buildRequest().get());// Не работает
        System.out.println("SDK " + graphClient.getServiceSDKVersion());
        System.out.println("ServiceRoot " + graphClient.getServiceRoot());
        /*DriveItem driveItem = graphClient.me().drive().root()
                .buildRequest()
                .get();
        System.out.println(driveItem);*/
      /*  User user = graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0")
                .buildRequest()
                .get();*/
        //System.out.println(objectMapper.writeValueAsString(user));
        //Получаем сведения о диске конкретного клиента
        //System.out.println("First look: " + objectMapper.writeValueAsString(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().root().children().buildRequest().get())); //root() тут после рута
//    DriveItemCollectionPage oDriveCollectionPage = graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().root().children().buildRequest().get(); //тут
//        System.out.println("Drive " + objectMapper.writeValueAsString(oDriveCollectionPage));
//        System.out.println("CurrentPage size" + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().size()));
//        System.out.println("CurrentPage zero" + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().get(4)));
//        System.out.println("--------------------------Try get content from nested folder---------------------");
//        //System.out.println("SubFolder Документы:  " + objectMapper.writeValueAsString(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVM3S4K2OOMNEZDLZB2AOMYHREDR").listItem().buildRequest().get())); так можем получить необходимый мета объект для определения что это
//        System.out.println("SubFolder Документы:  " + objectMapper.writeValueAsString(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVM3S4K2OOMNEZDLZB2AOMYHREDR").children().buildRequest().get())); //Рабочий вариант получения мета информации о контенте внутри опредёлённо дирректории
//        System.out.println("SubSubFolder Документы:  " + objectMapper.writeValueAsString(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVJUSN2MGYVEMRDLYROPNEYIVT6W").children().buildRequest().get()));
//        //System.out.println("Folder: " + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().get(0).folder));
//        //System.out.println("ListItem: " + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().get(0)));
//        //System.out.println("Children: " + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().get(0).children));
//        System.out.println("Try to get metaatribute file :  " + objectMapper.writeValueAsString(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT").buildRequest().get().webUrl));
//        //System.out.println("Try to get content file :  " + graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT").content().buildRequest().get());
//        //BufferedInputStream in = new BufferedInputStream(graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT").content().buildRequest().get());

       //System.out.println("Try to get content from: " + graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT").content().getRequestUrl());
        //System.out.println("Try to read content from Docx by Charsets.UTF_16LE: " + new String(ByteStreams.toByteArray(in), Charsets.UTF_16LE));
        //System.out.println("Try to read content from Docx by Charsets.UTF_16: " + new String(ByteStreams.toByteArray(in), Charsets.UTF_16));
        //System.out.println("Try to read content from Docx by JSON: " + objectMapper.writeValueAsString(ByteStreams.toByteArray(in)));
        //System.out.println("Try to read content from Docx by Charsets.UTF_16BE: " + new String(ByteStreams.toByteArray(in), Charsets.UTF_16BE));
        //System.out.println("Try to read content from Docx by Charsets.US_ASCII: " + new String(ByteStreams.toByteArray(in), Charsets.US_ASCII));
        //System.out.println("Try to read content from Docx by Charsets.ISO_8859_1: " + new String(ByteStreams.toByteArray(in), Charsets.ISO_8859_1));


       /* final String testDownloadFileId = "01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT";
        //final InputStream inputStream = (InputStream) graphClient.customRequest("/users/873d171c-8145-4586-b351-5dbea7fcf3a0/drive/items/"+testDownloadFileId+"/content", InputStream.class).buildRequest().get();
        //String sPathToDownLOad = (String) graphClient.customRequest("/users/873d171c-8145-4586-b351-5dbea7fcf3a0/drive/items/"+testDownloadFileId+"/content", String.class).buildRequest().get();
        try (FileInputStream fileInputStream = new FileInputStream("c://files//testFile2.docx")) {
            fileInputStream.read(ByteStreams.toByteArray(in));
        }
        LinkedList<Option> requestOptions = new LinkedList<Option>();
        requestOptions.add(new QueryOption("select", "@microsoft.graph.downloadUrl"));

        JsonObject oJsonObject  = (JsonObject) graphClient.customRequest("/users/873d171c-8145-4586-b351-5dbea7fcf3a0/drive/items/"+testDownloadFileId+"?select=@microsoft.graph.downloadUrl").buildRequest().get();
        System.out.println("oJsonObject: " + oJsonObject);*/
        /*InputStream inputStream = graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVKMWFSW6VWGXBGZQJI2VAA7G7TT").content()
                .buildRequest( requestOptions )
                .get();*/
        //System.out.println("Try content: " + driveItem.file);
       // System.out.println("Try to read content from docx: " + objectMapper.writeValueAsString(driveItem));

        //FileOutputStream out = new FileOutputStream("c://files//testFile2.txt");

        //out.write(ByteStreams.toByteArray(in));
        //out.write("someWords".getBytes(StandardCharsets.UTF_8));
       // out.close();
        /*System.out.println("Content " + inputStream);
        InputStreamReader isReader = new InputStreamReader(inputStream);
        //Creating a BufferedReader object
        BufferedReader reader = new BufferedReader(isReader);
        StringBuffer sb = new StringBuffer();
        String str;
        while((str = reader.readLine())!= null){
            sb.append(str);
        }
        //JSONObject testV = new JSONObject(new String(ByteStreams.toByteArray(inputStream)));
        System.out.println("Try to read conten from docx: " + sb);
                /*
        System.out.println("/////////////////////////////////////////graphClient");
        System.out.println("graphClient.drive() " + graphClient.drive());
        System.out.println("sites " + graphClient.sites());
        System.out.println("graphClient.drives() " + graphClient.drives());
        System.out.println("graphClient.directoryObjects() " + graphClient.directoryObjects());
        System.out.println("graphClient.teams() " + graphClient.teams());
        System.out.println("/////////////////////////////////////////graphClient.drive");
        System.out.println("Drive " + graphClient.drive().buildRequest().get().name);
        System.out.println("WEB_URL: " + graphClient.drive().buildRequest().get().webUrl);
        System.out.println("Root: " + graphClient.drive().buildRequest().get().root);
        System.out.println("graphClient.drive().buildRequest().get().driveType: " + graphClient.drive().buildRequest().get().driveType);
        System.out.println("graphClient.drive().buildRequest().get().items: " + graphClient.drive().buildRequest().get().items);
        System.out.println("graphClient.drive().buildRequest().get().special: " + graphClient.drive().buildRequest().get().special);
        //System.out.println("graphClient.drive().buildRequest().get().special: " + graphClient.drive().buildRequest().get().);
        System.out.println("////////////////////////-----------------------------graphClient.users(873d171c-8145-4586-b351-5dbea7fcf3a0)");
        System.out.println("Size: " + oDriveCollectionPage.getCurrentPage().size());
        System.out.println("ID: " + oDriveCollectionPage.getCurrentPage().get(0).id);
        System.out.println("Name: " + oDriveCollectionPage.getCurrentPage().get(0).name);
        System.out.println("Root: " + oDriveCollectionPage.getCurrentPage().get(0).root);
        System.out.println("webUrl: " + oDriveCollectionPage.getCurrentPage().get(0).webUrl);
        System.out.println("items: " + oDriveCollectionPage.getCurrentPage().get(0).items);
        System.out.println("description: " + oDriveCollectionPage.getCurrentPage().get(0).description);
        System.out.println("driveType: " + oDriveCollectionPage.getCurrentPage().get(0).driveType);
        System.out.println("eTag: " + oDriveCollectionPage.getCurrentPage().get(0).eTag);
        System.out.println("oDataType: " + oDriveCollectionPage.getCurrentPage().get(0).oDataType);
        System.out.println("createdBy: " + oDriveCollectionPage.getCurrentPage().get(0).createdBy);
        System.out.println("createdByUser: " + oDriveCollectionPage.getCurrentPage().get(0).createdByUser);
        System.out.println("createdDateTime: " + oDriveCollectionPage.getCurrentPage().get(0).createdDateTime);
        System.out.println("following: " + oDriveCollectionPage.getCurrentPage().get(0).following);
        System.out.println("list: " + oDriveCollectionPage.getCurrentPage().get(0).list);
        System.out.println("quota: " + oDriveCollectionPage.getCurrentPage().get(0).quota);
        System.out.println("sharePointIds: " + oDriveCollectionPage.getCurrentPage().get(0).sharePointIds);
        System.out.println("special: " + oDriveCollectionPage.getCurrentPage().get(0).special);
        System.out.println("system: " + oDriveCollectionPage.getCurrentPage().get(0).system);
        ///////////////////////
        System.out.println("////////////////////////////--------------Quota");
        System.out.println("oDataType: " + oDriveCollectionPage.getCurrentPage().get(0).quota.oDataType);
        System.out.println("state: " + oDriveCollectionPage.getCurrentPage().get(0).quota.state);
        System.out.println("total: " + oDriveCollectionPage.getCurrentPage().get(0).quota.total);
        System.out.println("remaining: " + oDriveCollectionPage.getCurrentPage().get(0).quota.remaining);
        //
        System.out.println("////////////////////////////--------------Identity");
        System.out.println("user: " + oDriveCollectionPage.getCurrentPage().get(0).createdBy.user.displayName);*/ //тут

       /* System.out.println("/////////////////////////////////////////Teams");
        System.out.println("Teams.Count " + graphClient.teams().buildRequest().get().getCount());*/

       /* System.out.println("/////////////////////////////////////////Sites"); //и тут
        System.out.println("Sites: " + graphClient.sites());
        System.out.println("Sites.RequestUrl: " + graphClient.sites().getRequestUrl());
        System.out.println("Sites.buildRequest: " + graphClient.sites().buildRequest().get());
        System.out.println("Sites.Count: " + graphClient.sites().buildRequest().get().getCount());*///и тут

        /*System.out.println("/////////////////////////////////////////Directory");
        System.out.println("Directory: " + graphClient.directory());
        System.out.println("Directory: " + graphClient.directory().buildRequest().get());*/

        /*System.out.println("/////////////////////////////////////////Shares");
       /System.out.println("Shares: " + graphClient.shares().buildRequest().get());*/
        /*
        //System.out.println("JSON for Users: " + objectMapper.writeValueAsString(graphClient.users().buildRequest().get()));
        System.out.println("JSON for Users: " + objectMapper.writeValueAsString(graphClient.users().buildRequest().get()));
        //System.out.println("JSON for shares: " + objectMapper.writeValueAsString(graphClient.shares().buildRequest().get())); invalidRequest
        //System.out.println("JSON for ME: " + objectMapper.writeValueAsString(graphClient.me().buildRequest().get())); //Request_ResourceNotFound
        System.out.println("JSON for Arguments: " + objectMapper.writeValueAsString(graphClient.agreements().buildRequest().get()));
        System.out.println("JSON for Application: " + objectMapper.writeValueAsString(graphClient.applications().buildRequest().get()));
        System.out.println("JSON for Branding: " + objectMapper.writeValueAsString(graphClient.branding().buildRequest().get()));
        //System.out.println("JSON for AppManareg: " + objectMapper.writeValueAsString(graphClient.deviceAppManagement().buildRequest().get()));
        System.out.println("JSON for Divices: " + objectMapper.writeValueAsString(graphClient.devices().buildRequest().get()));
        //System.out.println("JSON for Directory: " + objectMapper.writeValueAsString(graphClient.directory().buildRequest().get())); Request_InvalidRequestUrl. Error message: Request url was invalid. The request should be like /tenantdomainname/Entity or /$metadata. Tenant domain name can be any of the verified, unverified domain names or context id.
        //System.out.println("JSON for DirectoryObjects: " + objectMapper.writeValueAsString(graphClient.directoryObjects().buildRequest().get())); Request_UnsupportedQuery. Error message: Searches against this resource are not supported. Only specific instances can be queried.
        System.out.println("JSON for Drive: " + objectMapper.writeValueAsString(graphClient.drive().buildRequest().get()));
        System.out.println("JSON for Drives: " + objectMapper.writeValueAsString(graphClient.drives().buildRequest().get()));
        //System.out.println("JSON for Search: " + objectMapper.writeValueAsString(graphClient.search().buildRequest().get())); дичь
        System.out.println("JSON for Sites: " + objectMapper.writeValueAsString(graphClient.sites().buildRequest().get()));
        //System.out.println("JSON for Teams: " + objectMapper.writeValueAsString(graphClient.teams().buildRequest().get())); //'GET /teams' is not supported.
        System.out.println("JSON for Teamwork: " + objectMapper.writeValueAsString(graphClient.teamwork().buildRequest().get()));
        //System.out.println("JSON for TeamsTemplates: " + objectMapper.writeValueAsString(graphClient.teamsTemplates().buildRequest().get())); // 404 page not found
        //System.out.println("JSON for workbooks: " + objectMapper.writeValueAsString(graphClient.workbooks().buildRequest().get())); //Error message: Unable to find target address
        */
       // System.out.println("User Drive" + objectMapper.writeValueAsString(oDriveCollectionPage));
        //System.out.println("User Drive: " + objectMapper.writeValueAsString(oDriveCollectionPage.getCurrentPage().get(0).items)); //СКОРЕЕ ВСЕГО ИСПОЛЬЗОВАТЬ ЧЕРЕЗ ЭТОТ ПОДХОД!!!

       /* byte[] stream = Base64.getDecoder().decode("The contents of the file goes here.");
        DriveItem oDriveItem = new DriveItem();
        oDriveItem.
        graphClient.me().drive().items("01WFCTHVLLWM4LCEAIHVF2IC4LFPFETGMP")
                .buildRequest().put(oDriveItem);*/
        /*File f = new File("c://files//testFile2.docx");
        InputStream targetStream = new FileInputStream(f);
        long fileSize = (long) targetStream.available();

        UploadSession uploadSession =graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVLLWM4LCEAIHVF2IC4LFPFETGMP")
                .itemWithPath("some.docx")
                .createUploadSession(DriveItemCreateUploadSessionParameterSet.newBuilder().withItem(new DriveItemUploadableProperties()).build())
                .buildRequest()
                .post();
        LargeFileUploadTask<DriveItem> chunkedUploadProvider = new LargeFileUploadTask<DriveItem>(
                uploadSession,
                graphClient,
                targetStream,
                fileSize,
                DriveItem.class);

        final LargeFileUploadResult<DriveItem> result = chunkedUploadProvider.upload(0, null);
        System.out.println("JSON for LargeFileUploadResult: " + objectMapper.writeValueAsString(result));*/
        //----------------------
        LinkedList<String> asRoles = new LinkedList<String>();
        asRoles.add("write");

        String message = "Here's the file that we're collaborating on.";
        String sID_File = "01WFCTHVN4YTCKPFSLKRE32K3RVILOPNPY";

        List<String> asEmail_Users = Arrays.asList("nykyta_bondarenko@graphjava.onmicrosoft.com,panzer1594@gmail.com,CatDivision93@gmail.com".split("\\,"));
        LinkedList<DriveRecipient> aRecipientsList = new LinkedList<DriveRecipient>();
        for(String sEmail_User : asEmail_Users){
            DriveRecipient oRecipients = new DriveRecipient();
            oRecipients.email = sEmail_User;
            aRecipientsList.add(oRecipients);
        }

        graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items(sID_File)
                .invite(DriveItemInviteParameterSet
                .newBuilder()
                .withRequireSignIn(true)
                .withRoles(asRoles)
                .withSendInvitation(true)
                .withMessage(message)
                .withRecipients(aRecipientsList)
                .build())
                .buildRequest()
                .post();

        PermissionCollectionPage oPermissions =  graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items(sID_File)
                .permissions()
                .buildRequest()
                .get();

        String sResponse = objectMapper.writeValueAsString(oPermissions);

        System.out.println("Define sResponse" + sResponse);
        //-----
        //GraphServiceClient graphClient = GraphServiceClient.builder().authenticationProvider( authProvider ).buildClient();

//        String type = "edit";
//
//        String scope = "organization";
//
//        graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVN4YTCKPFSLKRE32K3RVILOPNPY")
//                .createLink(DriveItemCreateLinkParameterSet
//                        .newBuilder()
//                        .withType(type)
//                        .withScope(scope)
//                        .withExpirationDateTime(null)
//                        .withPassword(null)
//                        .withMessage(null)
//                        .build())
//                .buildRequest()
//                .post();
//
//        PermissionCollectionPage oPermissions =  graphClient.users("873d171c-8145-4586-b351-5dbea7fcf3a0").drive().items("01WFCTHVN4YTCKPFSLKRE32K3RVILOPNPY")
//                .permissions()
//                .buildRequest()
//                .get();
//
//        String sResponse = objectMapper.writeValueAsString(oPermissions);
//        System.out.println("Define sResponse" + sResponse);


    }

    //Возможно вообще не пригодится
    static final IProgressCallback callback = new IProgressCallback () {
        @Override
        public void progress(final long current, final long max) {
            //Check progress
        }
    };
}
