# LogsAnalysisTool
Tool scans directory for log files, read log files and filter log records that conform to user input and produce output.
## What is well done
- Clean commit history
- Implementation of layered architecture (dao, service, controller, view). All layers are independent which provides project extensibility. If one of the layers is changed the others don't know about it. Single responsibility principle is supported.
- Communication between layers through interfaces
- Design patterns (singleton, factory).
- Maven for build project

## Future improvements
- Add other grouping and filter parameters
- Make comments for code/create documentation
- Add tests
- Create validation layer
