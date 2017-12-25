-- Not very nice solution of https://www.hackerrank.com/challenges/occupations
SELECT drpr.drName, drpr.prName, siac.siName, siac.acName FROM
    (
        SELECT pr.RowNumber as RowNumber, dr.Name as drName, pr.Name as prName FROM

        (SELECT @n := @n + 1 RowNumber, o.Name
            FROM 
                (select @n:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Doctor'
            ORDER BY o.Name) dr

        RIGHT OUTER JOIN

        (SELECT @m := @m + 1 RowNumber, o.Name
            FROM 
                (select @m:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Professor'
            ORDER BY o.Name) pr

        ON pr.RowNumber = dr.RowNumber

        UNION 

        SELECT dr.RowNumber as RowNumber, dr.Name as drName, pr.Name as prName FROM

        (SELECT @n := @n + 1 RowNumber, o.Name
            FROM 
                (select @n:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Doctor'
            ORDER BY o.Name) dr

        LEFT OUTER JOIN

        (SELECT @m := @m + 1 RowNumber, o.Name
            FROM 
                (select @m:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Professor'
            ORDER BY o.Name) pr

        ON pr.RowNumber = dr.RowNumber
    ) drpr

LEFT OUTER JOIN

    (
            SELECT si.RowNumber as RowNumber, si.Name as siName, ac.Name as acName FROM

            (SELECT @k := @k + 1 RowNumber, o.Name
                FROM 
                    (select @k:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Singer'
                ORDER BY o.Name) si

            RIGHT OUTER JOIN

            (SELECT @l := @l + 1 RowNumber, o.Name
                FROM 
                    (select @l:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Actor'
                ORDER BY o.Name) ac

            ON si.RowNumber = ac.RowNumber

            UNION 

            SELECT ac.RowNumber as RowNumber, si.Name as siName, ac.Name as acName FROM

            (SELECT @k := @k + 1 RowNumber, o.Name
                FROM 
                    (select @k:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Singer'
                ORDER BY o.Name) si

            RIGHT OUTER JOIN

            (SELECT @l := @l + 1 RowNumber, o.Name
                FROM 
                    (select @l:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Actor'
                ORDER BY o.Name) ac

            ON si.RowNumber = ac.RowNumber
    ) siac

ON drpr.RowNumber = siac.RowNumber

UNION

SELECT drpr.drName, drpr.prName, siac.siName, siac.acName FROM
    (
        SELECT pr.RowNumber as RowNumber, dr.Name as drName, pr.Name as prName FROM

        (SELECT @n := @n + 1 RowNumber, o.Name
            FROM 
                (select @n:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Doctor'
            ORDER BY o.Name) dr

        RIGHT OUTER JOIN

        (SELECT @m := @m + 1 RowNumber, o.Name
            FROM 
                (select @m:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Professor'
            ORDER BY o.Name) pr

        ON pr.RowNumber = dr.RowNumber

        UNION 

        SELECT dr.RowNumber as RowNumber, dr.Name as drName, pr.Name as prName FROM

        (SELECT @n := @n + 1 RowNumber, o.Name
            FROM 
                (select @n:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Doctor'
            ORDER BY o.Name) dr

        LEFT OUTER JOIN

        (SELECT @m := @m + 1 RowNumber, o.Name
            FROM 
                (select @m:=0) initvars,
                OCCUPATIONS o
            WHERE o.Occupation = 'Professor'
            ORDER BY o.Name) pr

        ON pr.RowNumber = dr.RowNumber
    ) drpr

LEFT OUTER JOIN

    (
            SELECT si.RowNumber as RowNumber, si.Name as siName, ac.Name as acName FROM

            (SELECT @k := @k + 1 RowNumber, o.Name
                FROM 
                    (select @k:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Singer'
                ORDER BY o.Name) si

            RIGHT OUTER JOIN

            (SELECT @l := @l + 1 RowNumber, o.Name
                FROM 
                    (select @l:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Actor'
                ORDER BY o.Name) ac

            ON si.RowNumber = ac.RowNumber

            UNION 

            SELECT ac.RowNumber as RowNumber, si.Name as siName, ac.Name as acName FROM

            (SELECT @k := @k + 1 RowNumber, o.Name
                FROM 
                    (select @k:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Singer'
                ORDER BY o.Name) si

            RIGHT OUTER JOIN

            (SELECT @l := @l + 1 RowNumber, o.Name
                FROM 
                    (select @l:=0) initvars,
                    OCCUPATIONS o
                WHERE o.Occupation = 'Actor'
                ORDER BY o.Name) ac

            ON si.RowNumber = ac.RowNumber
    ) siac

ON drpr.RowNumber = siac.RowNumber
