package com.example.coronavirus;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PacienteContentProvider extends android.content.ContentProvider {
    private static final String AUTHORITY = "com.example.coronavirus";
    private static final String PACIENTES = "pacientes";
    private static final String DISTRITO = "distrito";
    private static final String SUSPEITOS = "suspeito";

    private static final Uri ENDERECO_BASE = Uri.parse("content://" + AUTHORITY);
    public static final Uri ENDERECO_PACIENTES = Uri.withAppendedPath(ENDERECO_BASE, PACIENTES);
    public static final Uri ENDERECO_DISTRITO = Uri.withAppendedPath(ENDERECO_BASE, DISTRITO);
    public static final Uri ENDERECO_SUSPEITOS = Uri.withAppendedPath(ENDERECO_BASE, SUSPEITOS);

    private static final int URI_DISTRITO = 100;
    private static final int URI_ID_DISTRITO = 101;
    private static final int URI_PACIENTES = 200;
    private static final int URI_ID_PACIENTES = 201;
    private static final int URI_SUSPEITOS = 300;
    private static final int URI_ID_SUSPEITOS = 301;

    private static final String CURSOR_DIR = "vnd.android.cursor.dir/";
    private static final String CURSOR_ITEM = "vnd.android.cursor.item/";

    private BdPacienteOpenHelper openHelper;

    private UriMatcher getUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY, DISTRITO, URI_DISTRITO);
        uriMatcher.addURI(AUTHORITY, DISTRITO + "/#", URI_ID_DISTRITO);

        uriMatcher.addURI(AUTHORITY, PACIENTES, URI_PACIENTES);
        uriMatcher.addURI(AUTHORITY, PACIENTES + "/#", URI_ID_PACIENTES);

        uriMatcher.addURI(AUTHORITY, SUSPEITOS, URI_SUSPEITOS);
        uriMatcher.addURI(AUTHORITY, SUSPEITOS + "/#", URI_ID_SUSPEITOS);

        return uriMatcher;
    }

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     * <p>You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via {@link #query}, {@link #insert}, etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     * <p>If you use SQLite, {@link SQLiteOpenHelper}
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * {@link SQLiteOpenHelper#getReadableDatabase} or
     * {@link SQLiteOpenHelper#getWritableDatabase}
     * from this method.  (Instead, override
     * {@link SQLiteOpenHelper#onOpen} to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    @Override
    public boolean onCreate() {
        openHelper = new BdPacienteOpenHelper(getContext());
        return true;
    }

    /**
     * Implement this to handle query requests from clients.
     *
     * <p>Apps targeting {@link Build.VERSION_CODES#O} or higher should override
     * implementation of this method.
     *
     * <p>This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     * <p>
     * Example client call:<p>
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:<p>
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri           The URI to query. This will be the full URI sent by the client;
     *                      if the client is requesting a specific record, the URI will end in a record number
     *                      that the implementation should parse and add to a WHERE or HAVING clause, specifying
     *                      that _id value.
     * @param projection    The list of columns to put into the cursor. If
     *                      {@code null} all columns are included.
     * @param selection     A selection criteria to apply when filtering rows.
     *                      If {@code null} then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     *                      the values from selectionArgs, in order that they appear in the selection.
     *                      The values will be bound as Strings.
     * @param sortOrder     How the rows in the cursor should be sorted.
     *                      If {@code null} then the provider is free to define the sort order.
     * @return a Cursor or {@code null}.
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase bd = openHelper.getReadableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_DISTRITO:
                return new BdTableDistrito(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_DISTRITO:
                return new BdTableDistrito(bd).query(projection, BdTableDistrito._ID + "=?", new String[] { id }, null, null, sortOrder);

            case URI_PACIENTES:
                return new BdTabelPaciente(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_PACIENTES:
                return new BdTabelPaciente(bd).query(projection, BdTabelPaciente._ID + "=?", new String[]{ id }, null, null, sortOrder);

            case URI_SUSPEITOS:
                return new BdTableSuspeitos(bd).query(projection, selection, selectionArgs, null, null, sortOrder);

            case URI_ID_SUSPEITOS:
                return new BdTableSuspeitos(bd).query(projection, BdTableSuspeitos._ID + "=?", new String[] { id }, null, null, sortOrder);

            default:
                throw new UnsupportedOperationException("Endereço query inválido: " + uri.getPath());
        }
    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * <code>vnd.android.cursor.item</code> for a single record,
     * or <code>vnd.android.cursor.dir/</code> for multiple items.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or {@code null} if there is no type.
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int codigoUri = getUriMatcher().match(uri);

        switch (codigoUri) {
            case URI_DISTRITO:
                return CURSOR_DIR + DISTRITO;
            case URI_ID_DISTRITO:
                return CURSOR_ITEM + DISTRITO;
            case URI_PACIENTES:
                return CURSOR_DIR + PACIENTES;
            case URI_ID_PACIENTES:
                return CURSOR_ITEM + PACIENTES;
            case URI_SUSPEITOS:
                return CURSOR_DIR + SUSPEITOS;
            case URI_ID_SUSPEITOS:
                return CURSOR_ITEM + SUSPEITOS;
            default:
                return null;
        }
    }

    /**
     * Implement this to handle requests to insert a new row.
     * * after inserting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri    The content:// URI of the insertion request. This must not be {@code null}.
     * @param values A set of column_name/value pairs to add to the database.
     *               This must not be {@code null}.
     * @return The URI for the newly inserted item.
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        long id;

        switch (getUriMatcher().match(uri)) {
            case URI_DISTRITO:
                id = (new BdTableDistrito(bd).insert(values));
                break;

            case URI_PACIENTES:
                id = (new BdTabelPaciente(bd).insert(values));
                break;

            case URI_SUSPEITOS:
                id = (new BdTableSuspeitos(bd).insert(values));
                break;

            default:
                throw new UnsupportedOperationException("Endereço insert inválido: " + uri.getPath());
        }

        if (id == -1) {
            throw new SQLException("Não foi possível inserir o registo: " + uri.getPath());
        }

        return Uri.withAppendedPath(uri, String.valueOf(id));
    }

    /**
     * Implement this to handle requests to delete one or more rows.
     * The implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * after deleting.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * <p>The implementation is responsible for parsing out a row ID at the end
     * of the URI, if a specific row is being deleted. That is, the client would
     * pass in <code>content://contacts/people/22</code> and the implementation is
     * responsible for parsing the record number (22) when creating a SQL statement.
     *
     * @param uri           The full URI to query, including a row ID (if a specific record is requested).
     * @param selection     An optional restriction to apply to rows when deleting.
     * @param selectionArgs
     * @return The number of rows affected.
     * @throws SQLException
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_DISTRITO:
                return new BdTableDistrito(bd).delete(BdTableDistrito._ID + "=?", new String[] { id });

            case URI_ID_PACIENTES:
                return new BdTabelPaciente(bd).delete(BdTabelPaciente._ID + "=?", new String[]{id});

           case URI_ID_SUSPEITOS:
                return new BdTableSuspeitos(bd).delete(BdTableSuspeitos._ID + "=?", new String[]{id});

            default:
                throw new UnsupportedOperationException("Endereço delete inválido: " + uri.getPath());
        }
    }

    /**
     * Implement this to handle requests to update one or more rows.
     * The implementation should update all rows matching the selection
     * to set the columns according to the provided values map.
     * after updating.
     * This method can be called from multiple threads, as described in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads">Processes
     * and Threads</a>.
     *
     * @param uri           The URI to query. This can potentially have a record ID if this
     *                      is an update request for a specific record.
     * @param values        A set of column_name/value pairs to update in the database.
     *                      This must not be {@code null}.
     * @param selection     An optional filter to match rows to update.
     * @param selectionArgs
     * @return the number of rows affected.
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase bd = openHelper.getWritableDatabase();

        String id = uri.getLastPathSegment();

        switch (getUriMatcher().match(uri)) {
            case URI_ID_DISTRITO:
                return new BdTableDistrito(bd).update(values,BdTableDistrito._ID + "=?", new String[] { id });

            case URI_ID_PACIENTES:
                return new BdTabelPaciente(bd).update(values, BdTabelPaciente._ID + "=?", new String[] { id });

            case URI_ID_SUSPEITOS:
                return new BdTableSuspeitos(bd).update(values, BdTableSuspeitos._ID + "=?", new String[] { id });

            default:
                throw new UnsupportedOperationException("Endereço de update inválido: " + uri.getPath());
        }
    }
}
