# DB Launcher

A command-line tool to quickly launch and manage local database instances using Docker.

## Usage

The `db` command provides several subcommands to manage database containers.

### `up`
Starts a new database container.

```shell
./db up <vendor> [options]
```

**Example:**
```shell
# Start a Postgres container with default settings
./db up postgres

# Start a MySQL container with a specific version and password
./db up mysql --version 8.0 --password mysecret
```

### `down`
Stops and removes a running database container.

```shell
./db down <vendor>
```

**Example:**
```shell
./db down postgres
```

### `logs`
Fetches and displays the logs of a running database container.

```shell
./db logs <vendor> [options]
```

**Example:**
```shell
# Show the logs for a Postgres container
./db logs postgres

# Follow the logs for a Postgres container
./db logs postgres --follow
```

### `list`
Lists all the `db-launcher` created containers that are currently running.

```shell
./db list
```

### Supported Vendors
- `postgres`
- `mysql`
- `mariadb`
- `mssql`
- `oracle`

---

## Building the Application

To build a single, self-contained executable JAR file, you need to configure the package type as `uber-jar`.

1.  **Package the application:**
    ```shell
    ./mvnw package
    ```

2.  **Run the application:**
    The uber-jar will be created in the `target/` directory and can be run directly.
    ```shell
    java -jar target/db-launcher-1.0.0-SNAPSHOT-runner.jar
    ```
    You can then rename this file to `db` and place it in your path for easy access.
